package net.commerce.zocalo.rpc;

import net.commerce.zocalo.service.MarketOwner;
import net.commerce.zocalo.claim.Position;
import net.commerce.zocalo.market.Market;
import net.commerce.zocalo.market.DuplicateOrderException;
import net.commerce.zocalo.user.User;
import net.commerce.zocalo.hibernate.HibernateUtil;
import net.commerce.zocalo.currency.Price;
import net.commerce.zocalo.currency.Quantity;

import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.apache.log4j.Logger;

// Copyright 2008 Jason Carver.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/**
 * RPCTrade is a helper class for RPCServer that facilitates one-off market trading
 *
 * Created by: Jason Carver
 * Date: Jan 16, 2008
 *
 * Modified by: Chris Hibbert
 * Date September 9, 2009
 */
public class RPCTrade {
    public void buy(String username, String marketName, String buySell, String position, Price price, int shares){
        final Transaction transaction = HibernateUtil.beginTransactionForJsp();
        Market market = getMarket(marketName);
        Position pos = market.getClaim().lookupPosition(position);
        User user = MarketOwner.getUser(username);
        
        //adjust price; football experiment module has scale 0 <= p <= 100
        try {
            market.limitOrder(pos, price, new Quantity(shares), user);
        } catch (DuplicateOrderException e) {
            e.printStackTrace();
            Logger logger = Logger.getLogger(RPCTrade.class);
            logger.error(e);
        }
        try {
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            Logger logger = Logger.getLogger(RPCTrade.class);
            logger.error(e);
        } finally {
            HibernateUtil.currentSession().close();
        }
    }

    public Market getMarket(String claimName) {
        return HibernateUtil.getMarketByName(claimName);
    }
}
