package net.commerce.zocalo.market;

// Copyright 2006-2009 Chris Hibbert.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.commerce.zocalo.ajax.events.MakerTrade;
import net.commerce.zocalo.ajax.events.PriceChange;
import net.commerce.zocalo.claim.Position;
import net.commerce.zocalo.currency.Coupons;
import net.commerce.zocalo.currency.Price;
import net.commerce.zocalo.currency.Probability;
import net.commerce.zocalo.currency.Quantity;
import net.commerce.zocalo.scoringrules.Logarithmic;
import net.commerce.zocalo.scoringrules.Quadratic;
import net.commerce.zocalo.scoringrules.Spherical;
import net.commerce.zocalo.user.User;

/** MultiMarketMaker is a MarketMaker for a Multi-position claim.  See {@link MarketMaker} for
    more details */
public class MultiMarketMaker extends MarketMaker {
    private Map<Position, Probability> probabilities;
    protected Map<Position, Quantity> stocks;

    MultiMarketMaker(MultiMarket market, Quantity subsidy, User owner) {
        super(market, subsidy, owner);
        System.out.println("constructor multimarket");
        Position[] positions = market.getClaim().positions();
        numOutcomes = positions.length;
        probabilities = new HashMap<Position, Probability>(positions.length);
        stocks = new HashMap<Position, Quantity>(positions.length);
        Probability initialValue = new Probability(1.0 / positions.length);
        for (int i = 0; i < positions.length; i++) {
            Position pos = positions[i];
            probabilities.put(pos, initialValue);
            stocks.put(pos, Quantity.ZERO);
        }
        recordInitialProbabilities(positions, initialValue, owner, stocks);
        System.out.println("stocks:" + stocks);
        System.out.println("probabilities:" + probabilities);
        initBeta(subsidy, positions.length);
    }

    /** @deprecated */
    MultiMarketMaker() {
    }

    void initBeta(Quantity endowment, int outcomeCount) {
        System.out.println("Initing beta");
        initBetaLog(endowment, outcomeCount, maxPrice());
        initBetaQuad(endowment, outcomeCount, maxPrice());
        initBetaSphere(endowment, outcomeCount, maxPrice());
    }
    
    private void initBetaSphere(Quantity endowment, int outcomeCount,
            Price maxPrice) {
        System.out.println("Initing betaS");
        Quantity rootOutcomes = new Quantity(Math.sqrt(numOutcomes));
        setBetaSpherical(endowment.div(maxPrice).div(rootOutcomes.minus(Quantity.ONE)));
        System.out.println("betaL = "+getBetaSpherical());
    }

    private void initBetaQuad(Quantity endowment, int outcomeCount,
            Price maxPrice) {
        System.out.println("Initing betaQ");
        setBetaQuadratic(endowment.times(new Quantity(numOutcomes)).div(new Quantity(numOutcomes-1)).div(maxPrice));
        System.out.println("betaL = "+getBetaQuadratic());
    }

    private void initBetaLog(Quantity endowment, int outcomeCount,
            Price maxPrice) {
        System.out.println("Initing betaL");
        Quantity logOutcomes = new Quantity(Math.log(numOutcomes));
        setBeta(endowment.div(maxPrice).div(logOutcomes));
        System.out.println("betaL = "+getBeta());
        
    }

    public synchronized Probability currentProbability(Position position) {
        return probabilities.get(position);
    }

    void recordTrade(String name, Quantity coupons, Quantity cost, Position position, Dictionary<Position, Probability> startProbs) {
        Dictionary<Position, Probability> endProbs = currentProbabilities(position);
        Enumeration keys = endProbs.keys();
        while (keys.hasMoreElements()) {
            Position pos = (Position) keys.nextElement();
            Price startP = scaleToPrice(startProbs.get(pos));
            Price endP = scaleToPrice(endProbs.get(pos));
            Price avePrice = market().asPrice(cost.div(coupons));
            if (pos.equals(position)) {
                if (startP.compareTo(endP) < 0) {
                    MakerTrade.newMakerTrade(name, avePrice, coupons, pos, startP, endP);
                } else {
                    MakerTrade.newMakerTrade(name, avePrice, coupons.negate(), pos, startP, endP);
                }
            } else {
                MakerTrade.newMakerTrade(name, avePrice, Quantity.ZERO, pos, startP, endP);
            }
        }
        scheduleChartGeneration();
        new PriceChange(position.getClaim().getName(), currentProbabilities(position));
    }

    private void recordInitialProbabilities(Position[] positions, Probability initialValue, User owner, Map<Position, Quantity> stocks) {
        for (int i = 0; i < positions.length; i++) {
            Position position = positions[i];
            Price scaledPrice = scaleToPrice(initialValue);
            MakerTrade.newMakerTrade(owner.getName(), scaledPrice, stocks.get(position), position, scaledPrice, scaledPrice);
        }
    }

    Dictionary<Position, Probability> currentProbabilities(Position ignore) {
	System.out.println("in currentProbs..");
        Hashtable<Position, Probability> probs = new Hashtable<Position, Probability>();
        Set keys = probabilities.keySet();
        for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
            Position pos = (Position) iterator.next();
            probs.put(pos, currentProbability(pos));
            System.out.println(pos.getName() +"\t" + currentProbability(pos));

        }
        return probs;
    }

//// TRADING /////////////////////////////////////////////////////////////////

    Set<Coupons> provideCouponSet(Position position, Quantity totalShares, boolean increasing) {
        HashSet<Coupons> couponSet = new HashSet<Coupons>();
        if (increasing) {
            Coupons coupons = provideCoupons(position, totalShares);
            couponSet.add(coupons);
        } else {
            ensureOpposingSetsAvailable(position, totalShares);
            addOpposingShares(position, couponSet, totalShares);
        }
        return couponSet;
    }

    private void addOpposingShares(Position position, HashSet<Coupons> couponSet, Quantity totalShares) {
        Position[] positions = position.getClaim().positions();
        for (int i = 0; i < positions.length; i++) {
            Position pos = positions[i];
            if (! pos.equals(position)) {
                couponSet.add(accounts().provideCoupons(pos, totalShares));
            }
        }
    }

    void scaleProbabilities(Position position, Probability newProb) {
       // scale others by (1 - newProb)/(1 - oldProb) ; position gets remainder
	System.out.println("in scaleProbabilities");
        Probability oldProb = currentProbability(position);
        Quantity scale = newProb.inverted().div(oldProb.inverted());
        Quantity totalProb = Quantity.ZERO;
	System.out.println("oldProb: "+ oldProb.printAsCents() + "\t newProb: "+ newProb.printAsCents());
        System.out.println("scale: "+ scale.printAsQuantity()); 
        
        for (Iterator iterator = probabilities.keySet().iterator(); iterator.hasNext();) {
            Position pos = (Position)iterator.next();
            if (pos != position) {
                Probability newP = new Probability(scale.times(currentProbability(pos)));
                setProbability(pos, newP);
                totalProb = totalProb.plus(newP);
            }
        }
        setProbability(position, new Probability(totalProb).inverted());
    }

    synchronized void setProbability(Position position, Probability probability) {
        probabilities.put(position, probability);
    }
    
    /** @deprecated */
    public synchronized Map getProbabilities() {
        return probabilities;
    }
    
    /** @deprecated */
    public synchronized void setProbabilities(Map<Position, Probability> probabilities) {
        this.probabilities = probabilities;
    }
    
    synchronized void setStock(Position position, Quantity quantity) {
        stocks.put(position, quantity);
    }

    public synchronized Map getStocks() {
        return stocks;
    }
    
    
    synchronized void setStocks(Map<Position, Quantity> quatities) {
        this.stocks = quatities;
    }
    
    public synchronized Quantity currentStock(Position position) {
        return stocks.get(position);
    }

    Quantity incrC(Position position, Probability targetProbability) {
        numOutcomes = market.getClaim().positions().length;
        Quantity q = Logarithmic.incrC(position, currentProbability(position), targetProbability, stocks, getBeta(), numOutcomes);
        System.out.println("Calculated q incrC:" + q);
        return q;
    }

    /** what would the probability be after buying QUANT coupons? */
    protected Probability newPFromIncrC(Position position, Quantity quantity) {
        numOutcomes = market.getClaim().positions().length;
        @SuppressWarnings("deprecation")
		Probability prob = Logarithmic.newPFromIncrC(position, quantity, currentProbability(position), stocks, getBeta(), numOutcomes);
        System.out.println("Calculated newPFromIncrC:" + prob);
        return prob;
    }

    /** The money price charged to move the probability from p to newP is |B * log((1 - newP)/(1 - p)| * couponCost*/
    protected Quantity baseC(Position position, Probability targetProbability) {
        numOutcomes = market.getClaim().positions().length;
        System.out.println("Stocks baseC:" + stocks);
        @SuppressWarnings("deprecation")
		Quantity q = Logarithmic.baseC(position, currentProbability(position), targetProbability, stocks, getBeta(), numOutcomes);
        System.out.println("Calculated q baseC:" + q);
        return q;
    }

    /** what would the probability be after spending COST?   After spending COST,
     the user has gained COST new pairs, and will trade the undesired coupons for
     desirable ones.  The new probability will be (1 - ((1-p)*exp(COST)).   */
    Probability newPFromBaseC(Position position, Quantity cost) {
        numOutcomes = market.getClaim().positions().length;
        @SuppressWarnings("deprecation")
		Probability prob = Logarithmic.newPFromBaseC(position, cost, currentProbability(position), stocks, getBeta(), numOutcomes);
        System.out.println("Calculated newPFromBaseC:" + prob);
        return prob;
    }

    protected Probability newPFromTotalC(Position position, Quantity totalC) {
    	System.out.println("***********Calculated newPFromTotalC:");
        numOutcomes = market.getClaim().positions().length;
        @SuppressWarnings("deprecation")
		Probability prob = Logarithmic.newPFromTotalC(position, totalC, currentProbability(position), stocks, getBeta(), numOutcomes);
        System.out.println("Calculated newPFromTotalC:" + prob);
        return prob;
    }

    //  totalC is |baseC - incrC|.  (BaseC and IncrC have opposite signs)
    //  baseC = beta*|log((1 - newP) / (1 - p))|      incrC =  beta*|log(newProb/prob)|
    //     totalC = beta * | log((1 - newP) / (1 - p)) / (newProb/prob)) |
    //  so totalC = beta * | log(newP * (1 - p) / (p * (1 - newP))) |
    protected Quantity totalC(Position position, Probability newP) {
        @SuppressWarnings("deprecation")
		Quantity q = Logarithmic.totalC(position, currentProbability(position), newP, stocks, getBeta(), numOutcomes);
        System.out.println("Calculated q totalC:" + q);
        return q;
    }

}
