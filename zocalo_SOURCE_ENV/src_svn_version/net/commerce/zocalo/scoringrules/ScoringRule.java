package net.commerce.zocalo.scoringrules;

import net.commerce.zocalo.claim.Position;
import net.commerce.zocalo.currency.Probability;
import net.commerce.zocalo.currency.Quantity;
import java.util.Map;

public class ScoringRule {
    
    /*protected double beta;
    protected int numOutcomes;
    
    protected void initBeta(Quantity endowment, Quantity maxPrice);
    protected Quantity getBeta(){
        return new Quantity(beta);
    }*/
    
    public static Quantity incrC(Position position, Probability curP, Probability targetP, Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        return null;
    }
    public static Quantity baseC(Position position, Probability curP, Probability targetP, Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        return null;
    }
    public static Quantity totalC(Position position, Probability curP, Probability targetP, Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        return null;
    }
    
    public static Probability newPFromIncrC(Position position, Quantity limit, Probability curP, Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        return null;
    }
    public static Probability newPFromBaseC(Position position, Quantity limit, Probability curP, Map<Position, Quantity> stocks, Quantity beta, int numOutcomes) {
        return null;
    }
    public static Probability newPFromTotalC(Position position, Quantity limit, Probability curP, Map<Position, Quantity> newParam, Quantity beta, int numOutcomes) {
        return null;
    }

}
