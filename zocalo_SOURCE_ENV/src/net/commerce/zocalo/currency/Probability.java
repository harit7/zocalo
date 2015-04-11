package net.commerce.zocalo.currency;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

// Copyright 2009 Chris Hibbert.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

public class Probability extends RangedQuantity {
    final static MathContext PRINT_CONTEXT = new MathContext(4, RoundingMode.HALF_EVEN);
    private static Quantity CERTAINTY = new Quantity(BigDecimal.ONE);
    public static final Probability HALF = new Probability(new BigDecimal(".5"));
    public static final Probability NEVER = new Probability(BigDecimal.ZERO);
    public static final Probability ALWAYS = new Probability(BigDecimal.ONE);

    /** @deprecated */
    Probability() {
    }

    public Probability(String s) {
        super(s, CERTAINTY);
        verify();
    }

    public Probability(BigDecimal d) {
    	super(d,CERTAINTY);
    
    }

    public Probability(Quantity q) {
        super(q, CERTAINTY);
        verify();
    }

    public Probability(double v) {
        super(v, ONE);
        verify();
    }

    public Probability inverted() {
        return ALWAYS.minus(this);
      
    }

    public Probability minus(Quantity q) {
        BigDecimal decimal = quant().subtract(q.quant(), NINE_DIGITS);
        return new Probability(decimal);
    }

    public Probability min(Probability other) {
        return new Probability(quant().min(other.quant()));
    }

    public Probability max(Probability other) {
        return new Probability(quant().max(other.quant()));
    }

    public Probability round(MathContext c) {
        return new Probability(quant().round(c));
    }

    public Quantity getMaxValue() {
        return CERTAINTY;
    }

    public Quantity odds() {
        return this.div(this.inverted());
    }

    public Probability newValue(Quantity quantity) {
        return new Probability(quantity);
    }

    public Probability newValue(BigDecimal quantity) {
        return new Probability(quantity);
    }

    public Probability asProbability() {
        return this;
    }

    public String printAsIntegerPercent() {
        return quant().movePointRight(2).setScale(0, NINE_DIGITS.getRoundingMode()).toString();
    }

    public String printAsPercent(int scale) {
        return quant().movePointRight(2).setScale(scale, NINE_DIGITS.getRoundingMode()).toString();
    }

    public Probability simplify() {
        return new Probability(quant().stripTrailingZeros());
    }

    public int compareTo(Quantity quantity) {
        if (quantity instanceof Probability) {
            Probability other = (Probability)quantity;
            return super.compareTo(other);
        } else {
            throw new ClassCastException("don't compare Probabilities with other Quantities.");
        }
    }

    public String printAsCents() {
    	 
         Quantity q =  movePointLeft(-2).round(PRINT_CONTEXT);
         return q.quant.toPlainString();
    }
    
    public void verify()
    {
    	 
    	double value = quant.doubleValue() ; 
    	//double scale = Math.pow(10, 4);
    	Quantity tmpQ = new Quantity(quant);
        if(tmpQ.minus(EPSILON).isNegative()) {
            System.out.println("\n***************\nDANGER ZERO\n*****************");
            value = 0.0001;
        }
        else if (tmpQ.minus(ALWAYS).isPositive() || tmpQ.equals(ALWAYS) ) { 
            System.out.println("\n***************\nDANGER ONE\n*****************");
            value = 0.9999;
        }
        quant = new BigDecimal(value);
        quant = quant.round(PRINT_CONTEXT).stripTrailingZeros();
        System.out.println("in" + tmpQ +"out: "+ quant);;
       // CERTAINTY = new Quantity(value); 
    }
    
 
}
