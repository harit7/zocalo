package net.commerce.zocalo.market;

import net.commerce.zocalo.user.User;
import net.commerce.zocalo.claim.Position;
import net.commerce.zocalo.claim.Claim;
import net.commerce.zocalo.currency.*;
import net.commerce.zocalo.ajax.events.MakerTrade;
import net.commerce.zocalo.ajax.events.PriceChange;
import net.commerce.zocalo.freechart.ChartScheduler;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
// Copyright 2006-2009 Chris Hibbert.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/** Automated Market Maker for Binary Claims. */
public class BinaryMarketMaker extends MarketMaker {
    private Probability probability;

    public BinaryMarketMaker(Market market, User owner, Quantity endowment, Probability initialProbability) {
        super(market, endowment, owner);
        probability = initialProbability;
        Probability minP = probability.min(probability.inverted());
        initBetaExplicit(endowment, minP);
        Position[] positions = market.getClaim().positions();
        System.out.println("Number of positions in binary market:" + positions.length);
        numOutcomes = 2;
        //stocks = new HashMap<Position, Quantity>(positions.length);
        for (int i = 0; i < numOutcomes; i++) {
            //stocks.put(positions[i], Quantity.ZERO);
        }
    }

    public BinaryMarketMaker() {
    }

    public synchronized Probability currentProbability(Position position) {
        BinaryMarket market = (BinaryMarket) getMarket();
        if (position == market.getBinaryClaim().getYesPosition()) {
            return getProbability();
        } else {
            return getProbability().inverted();
        }
    }

    Set<Coupons> provideCouponSet(Position position, Quantity shares, boolean buying) {
        Coupons coupons;
        if (buying) {
            coupons = provideCoupons(position, shares);
        } else {
            Position opp = opposingPosition(position);
            coupons = provideCoupons(opp, shares);
        }
        HashSet<Coupons> couponsAsSet = new HashSet<Coupons>();
        couponsAsSet.add(coupons);
        return couponsAsSet;
    }

    void recordTrade(String name, Quantity coupons, Quantity cost, Position position, Dictionary<Position, Probability> startProbs) {
        Probability startP = startProbs.get(position);
        Price scaledOpen = scaleToPrice(startP);
        Price scaledClose = scaleToPrice(currentProbability(position));
        Price scaledAverage = new Price(cost.div(coupons), scaledOpen);
        if (scaledClose.compareTo(scaledOpen) > 0) {
            MakerTrade.newMakerTrade(name, scaledAverage, coupons, position, scaledOpen, scaledClose);
        } else {
            MakerTrade.newMakerTrade(name, scaledAverage, coupons.negate(), position, scaledOpen, scaledClose);
        }
        String claimName = position.getClaim().getName();
        scheduleChartGeneration();
        new PriceChange(claimName, currentProbabilities(position));
    }

    Dictionary<Position, Probability> currentProbabilities(Position pos) {
        Hashtable<Position, Probability> pTable = new Hashtable<Position, Probability>();
        Probability prob = currentProbability(pos);
        pTable.put(pos, prob);
        pTable.put(pos.opposite(), prob.inverted());
        return pTable;
    }

    void scaleProbabilities(Position position, Probability newProb) {
        if (position == ((BinaryMarket)getMarket()).getBinaryClaim().getYesPosition()) {
            setProbability(newProb);
        } else {
            setProbability(newProb.inverted());
        }
    }

    private Position opposingPosition(Position position) {
        Claim claim = position.getClaim();
        Position[] positions = claim.positions();
        for (int i = 0; i < positions.length; i++) {
            Position pos = positions[i];
            if (! pos.equals(position)) {
                return pos;
            }
        }
        return null;
    }

    /** @deprecated */
    public synchronized void setProbability(Probability probability) {
        this.probability = probability;
    }

    /** @deprecated */
    public synchronized Probability getProbability() {
        return probability;
    }

}
