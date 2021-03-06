package net.commerce.zocalo.experiment.states;
// Copyright 2008, 2009 Chris Hibbert.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/** Parent of {@link JudgingTransitionAdaptor JudgingTransitionAdaptors}.  Override methods to implement. */
public class NoActionJudgingTransitionAdaptor extends JudgingTransitionAdaptor {
    public boolean endJudging() {
        return false;
    }

    public void endTrading() {
        // Override to do something
    }

    public void startRound() {
        // Override to do something
    }

    public void warn(String warning) {
        // Override to do something
    }
}
