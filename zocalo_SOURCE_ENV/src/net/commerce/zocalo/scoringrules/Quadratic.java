package net.commerce.zocalo.scoringrules;

import java.util.Map;

import net.commerce.zocalo.claim.Position;
import net.commerce.zocalo.currency.Probability;
import net.commerce.zocalo.currency.Quantity;

public class Quadratic extends ScoringRule {

    /*public Quadratic(Quantity endowment, Quantity maxPrice, int num) {
        this.numOutcomes = num;
        initBeta(endowment, maxPrice);
    }
    
    protected void initBeta(Quantity endowment, Quantity maxPrice) {
        this.beta = endowment.times(new Quantity(numOutcomes)).div(new Quantity(numOutcomes-1)).div(maxPrice).asValue().doubleValue();
    }*/

    public static Quantity incrC(Position position, Probability curP,
            Probability targetP, Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        Quantity p=curP.asQuantity();
        Quantity pp=targetP.asQuantity();
        return (pp.minus(p)).times(pp.plus(p).minus(new Quantity(2.0))).times(beta.times(new Quantity(numOutcomes))).div(new Quantity(numOutcomes).minus(new Quantity(1))).abs();
    }

    public static Quantity baseC(Position position, Probability curP,
            Probability targetP, Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        Probability currentInverted = curP.inverted();
        Probability targetInverted = targetP.inverted();
        if (targetInverted.isZero() || currentInverted.isZero()) {
            throw new ArithmeticException("probabilities can't be zero or one.");
        }
        return beta.times(new Quantity(numOutcomes)).div(new Quantity(numOutcomes).minus(new Quantity(1))).times(targetP.times(targetP).minus(curP.times(curP))).abs();
    }

    public static Quantity totalC(Position position, Probability curP,
            Probability targetP, Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        Quantity p=curP.asQuantity();
        Quantity pp=targetP.asQuantity();
        return beta.times(new Quantity(numOutcomes).times(new Quantity(2)).div(new Quantity(numOutcomes).minus(new Quantity(1)))).times(pp.minus(p)).abs();
    }

    public static Probability newPFromIncrC(Position position, Quantity limit,
            Probability curP,  Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        return new Probability((new Quantity(numOutcomes).minus(new Quantity(1)).times(limit).div(beta.times(new Quantity(numOutcomes).times(new Quantity(2.0))))));
    }

    public static Probability newPFromBaseC(Position position, Quantity limit,
            Probability curP,  Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        Quantity baseC = limit.div(beta);
        Quantity frac=(new Quantity(numOutcomes).minus(Quantity.ONE)).div(new Quantity(numOutcomes));
        
        return new Probability((baseC.times(frac).plus(curP.times(curP))).sqrt());
    }

    public static Probability newPFromTotalC(Position position, Quantity limit,
            Probability curP,  Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        Quantity q=limit.times(new Quantity(numOutcomes).minus(new Quantity(1))).div(beta.times(new Quantity(2)).times(new Quantity(numOutcomes)));
        return new Probability(curP.minus(q));
    }


}
