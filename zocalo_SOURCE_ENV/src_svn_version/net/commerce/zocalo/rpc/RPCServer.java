package net.commerce.zocalo.rpc;

import net.commerce.zocalo.claim.Position;
import net.commerce.zocalo.currency.CashBank;
import net.commerce.zocalo.currency.Funds;
import net.commerce.zocalo.currency.Price;
import net.commerce.zocalo.currency.Quantity;
import net.commerce.zocalo.hibernate.HibernateUtil;
import net.commerce.zocalo.logging.GID;
import net.commerce.zocalo.market.BinaryMarket;
import net.commerce.zocalo.market.Market;
import net.commerce.zocalo.market.MultiMarket;
import net.commerce.zocalo.service.MarketOwner;
import net.commerce.zocalo.service.PropertyKeywords;
import net.commerce.zocalo.service.ServletUtil;
import net.commerce.zocalo.user.SecureUser;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.io.IOException;
import java.math.BigDecimal;

// Copyright 2009 Chris Hibbert.  All rights reserved.
// Copyright 2008 Jason Carver.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/**
 * RPCServer provides an external API for agents to interact with the server.
 *
 * The available abilities include trading shares on a market, opening and closing markets,
 * and depositing cash.  Accessing this interface from a client requires using the
 * Apache web services xmlrpc library. (http://ws.apache.org/xmlrpc/)
 *
 * The API typically gives a String response, starting with either "ERROR:" or "SUCCESS:",
 * followed by a human-readable response with details of the transaction or a reason for
 * the failure.<p>
 *
 * Created by: Jason Carver<br>
 * Date: Jan 16, 2008<br>
 * Edited by: Chris Hibbert starting June 15, 2009
 */
public class RPCServer {

    //initialize and run the RPCServer for XML API
    public void run(String portNumber) {
        int port;
        if (null == portNumber || portNumber.length() == 0) {
            port = 8180;
        } else {
            port = Integer.parseInt(portNumber);
        }
        WebServer server = new WebServer(port);
        XmlRpcServer xrs = server.getXmlRpcServer();
        PropertyHandlerMapping handlers = new PropertyHandlerMapping();
        try {
            handlers.addHandler("markets", RPCHandler.class);
        } catch(XmlRpcException e) {
            e.printStackTrace();
            throw new RuntimeException("RPC Server didn't add handler correctly");
        }
        xrs.setHandlerMapping(handlers);

        XmlRpcServerConfigImpl serverConfig =
            (XmlRpcServerConfigImpl) xrs.getConfig();
        serverConfig.setContentLengthOptional(false);
        serverConfig.setKeepAliveEnabled(true);

        //Set server to accept non-compliant xml-rpc calls from clients, and respond in kind.
        //This enables features otherwise impossible under xml-rpc protocol
        serverConfig.setEnabledForExtensions(true);

        try {
            server.start();
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("RPC Server didn't start correctly");
        }
    }

    /**
     * RPCHandler class specifies the namespace and actual interface available
     * for external XML-RPC calls
     */
    public static class RPCHandler {

        public RPCHandler(){
            super();
        }

        /**
         * This method adds an arbitrary amount of money to a user's account.
         *
         * It shouldn't be available in production environments, but
         *  is necessary when dealing with unreliable agent code.
         *
         * @param userName to receive the new cash
         * @param amt of cash to receive
         * @return result "ERROR:" or "SUCCESS:" followed with human-readable details
         */
        public String grantCash(String userName, BigDecimal amt){
            Logger logger;
            String goal;
            SecureUser user;
            Quantity amount = new Quantity(amt);

            synchronized (ServletUtil.servletLock) {
                final Transaction transaction = HibernateUtil.beginTransactionForJsp();
                //TODO make secure (this completely circumvents any security)
                user = HibernateUtil.getUserByName(userName);

                logger = Logger.getLogger(RPCHandler.class);

                if (userName == null || userName.equals("")) {
                    return "ERROR: No username provided -- " + userName;
                } else if (amount.isNegative()) {
                    return "ERROR: Cash amount negative: " + amount;
                } else if (amount.isZero()) {
                    return "SUCCESS: Awarded 0 cash to " + userName;
                }

                logger.warn("Granting cash (" + amount + ") directly to user [" + userName + "].");
                goal = "grant user " + userName + " cash in the amount of: " + amount;

                CashBank rootBank = HibernateUtil.getOrMakePersistentRootBank("rootCashBank");
                Funds funds = rootBank.makeFunds(amount);
                user.receiveCash(funds);
                try {
                    transaction.commit();
                } catch (HibernateException e) {
                    transaction.rollback();
                    e.printStackTrace();
                    logger.error(e);
                    logger.warn("Failed to " + goal);
                    return "ERROR: Grant Cash failed with HibernateException during commit -- " + amount;
                } finally {
                    HibernateUtil.currentSession().close();
                }
            }

            logger.info(GID.log() + "Completed " + goal);

            String warnings = "";
            if (user.hasWarnings()) {
                warnings = ", with warnings: " + user.getWarningsHTML();
            }

            return "SUCCESS: Awarded " + amount + " cash to " + userName + warnings;
        }
        
        public String closeMarket(String userName, String marketName, String position){
            Logger logger = Logger.getLogger(RPCHandler.class);

            if (userName == null || marketName == null) {
                return "ERROR: Market close request failed to specify owner or market name";
            }

            Market market = HibernateUtil.getMarketByName(marketName);
            if (market == null) {
                return "ERROR: Market to close does not exist: " + marketName;
            }

            SecureUser user;
            String goal;
            String redeemed;

            synchronized (ServletUtil.servletLock) {

                final Transaction transaction = HibernateUtil.beginTransactionForJsp();
                user = HibernateUtil.getUserByName(userName);
                if (! market.getOwner().equals(user)) {
                    return "ERROR: Market not closed; owner not found.";
                }

                goal = "close market " + marketName + " in favor of the position: " + position + " on authority of " + userName;
                logger.info(GID.log() + "Attempting to " + goal);

                Position winner = market.getClaim().lookupPosition(position);
                Quantity couponsRedeemed = market.decideClaimAndRecord(winner);
                redeemed = ".  Redeemed " + couponsRedeemed.printAsQuantity() + " pairs.";

                try {
                    transaction.commit();
                } catch (HibernateException e) {
                    transaction.rollback();
                    e.printStackTrace();
                    logger.error(e);
                    logger.info(GID.log() + "Failed to " + goal);
                    return "ERROR: Market close failed with HibernateException during commit -- " + marketName;
                } finally {
                    HibernateUtil.currentSession().close();
                }
            }

            logger.info(GID.log() + "Completed " + goal + redeemed);

            String warnings = "";
            if (user.hasWarnings()) {
                warnings = ", with warnings: " + user.getWarningsHTML();
            }

            return "SUCCESS: " + goal + redeemed + warnings;
        }

        public String createMarket(String userName, String marketName, String outcomes, String description, int endowAMM){
            if (userName == null || marketName == null || outcomes == null) {
                return "ERROR: user name, market name, or outcomes is null.";
            }

            Logger logger;
            String goal;
            SecureUser user;

            synchronized (ServletUtil.servletLock) {
                final Transaction transaction = HibernateUtil.beginTransactionForJsp();
                //TODO make secure (this completely circumvents any security)
                user = HibernateUtil.getUserByName(userName);
                boolean multiOutcome = !outcomes.equals("");

                logger = Logger.getLogger(RPCHandler.class);

                if (user == null || user.cashOnHand().compareTo(new Quantity(endowAMM)) < 0){
                    return "ERROR: user doesn't exist or doesn't have enough cash to create market.";
                } else if (HibernateUtil.getMarketByName(marketName) != null){
                    return "ERROR: Market already exists";
                } else if (multiOutcome) {
                    if (!outcomes.contains(",")){
                        return "ERROR: Outcomes must be a comma separated list of non-empty Strings";
                    }
                    else if (endowAMM < 1){
                        return "ERROR: Multi-outcome markets MUST have an endowed market maker";
                    }
                }

                goal = "create market " + marketName + " endowed with: " + endowAMM + " on authority of " + userName;
                logger.info(GID.log() + "Attempting to " + goal);

                Market market;
                if (multiOutcome){
                    market = endowMultiMarket(marketName, outcomes, endowAMM, user);
                } else {
                    market = endowBinaryMarket(marketName, endowAMM, user);
                }
                market.getClaim().setDescription(description);

                try {
                    transaction.commit();
                } catch (HibernateException e) {
                    transaction.rollback();
                    e.printStackTrace();
                    logger.error("Failed (during commit) to: " + goal, e);
                    return "ERROR: Hibernate Exception during commit";
                } finally {
                    HibernateUtil.currentSession().close();
                }
            }

            logger.info(GID.log() + "Completed " + goal);

            String warnings = "";
            if (user.hasWarnings()) {
                warnings = ", with warnings: " + user.getWarningsHTML();
            }

            return "SUCCESS: " + goal + warnings;
        }

        private BinaryMarket endowBinaryMarket(String marketName, int endowAMM, SecureUser user) {
            BinaryMarket bin = MarketOwner.newBinaryMarket(marketName, user);
            if (endowAMM > 0) {
                bin.makeMarketMaker(user, new Quantity(endowAMM));
            }
            return bin;
        }

        private MultiMarket endowMultiMarket(String marketName, String outcomes, int endowAMM, SecureUser user) {
            MultiMarket multi = MarketOwner.newMultiMarket(marketName, user, outcomes.split(PropertyKeywords.COMMA_SPLIT_RE));
            multi.makeMarketMaker(new Quantity(endowAMM), user);
            return multi;
        }

        public String tradeClaim(String userName, String marketName, String buySell, String position, int price, int shares){
            Logger logger = Logger.getLogger(RPCHandler.class);

            SecureUser user;
            String goal;

            synchronized (ServletUtil.servletLock) {
                final Transaction transaction = HibernateUtil.beginTransactionForJsp();
                //TODO make secure (this completely circumvents any security)
                user = HibernateUtil.getUserByName(userName);
                try {
                    transaction.commit();
                } catch (HibernateException e) {
                    logger.error("Failed while committing after getUser()", e);
                    return "ERROR: Hibernate Exception during commit";
                } finally {
                    HibernateUtil.currentSession().close();
                }

                if (userName == null || marketName == null || buySell == null) {
                    return "ERROR: Trade not attempted because of invalid parameters";
                } else if (price < 0 || price > 100) {
                    return "ERROR: Trade order failed because the price was out of bounds";
                } else if (HibernateUtil.getMarketByName(marketName) == null) {
                    return "ERROR: Trade order failed because market doesn't exist";
                } else if (user == null) {
                    return "ERROR: Trade order failed because user doesn't exist";
                }

                goal = "trade claim for " + userName + ": " + buySell + " " + shares + " shares at " + price + " on " + marketName;
                logger.info(GID.log() + "Attempting to " + goal);

                RPCTrade trade = new RPCTrade();
                trade.buy(userName, marketName, buySell, position, Price.dollarPrice(price), shares);
            }

            String warnings = "";
            if (user.hasWarnings()) {
                warnings = ", with warnings: " + user.getWarningsHTML();
            }

            logger.info(GID.log() + "Completed " + goal);
            return "SUCCESS: Zocalo RPC order to " + goal + warnings;
        }

        /**
         * This method responds with a Double, the price that the market maker is offering.
         *
         * @param marketName the market for which
         * @return marketMakerPrice price offered by Automated Market Maker, or null in case of error
         */
        public String getMarketMakerPrice(String marketName, String positionName){
            Logger logger = Logger.getLogger(RPCHandler.class);

            Market market = HibernateUtil.getMarketByName(marketName);
            if (marketName == null || market == null || positionName == null || positionName.equals("")){
                return null;
            }

            Position position = market.getClaim().lookupPosition(positionName);

            if (position == null){
                return null;
            }

            Price newPrice = new Price(market.currentProbability(position), market.maxPrice());
            logger.info(GID.log() + "Completed retrieval of market maker price for position " + positionName + " in " + marketName + ", price is " + newPrice);
            return newPrice.printAsWholeCents();
        }

        /**
         * Transfer a positive balance from one user account to another.
         * @param fromUserName
         * @param toUserName
         * @param amt
         * @return result "ERROR:" or "SUCCESS:" followed with human-readable details
         */
        public String transferCash(String fromUserName, String toUserName, BigDecimal amt) {
            final Transaction transaction = HibernateUtil.beginTransactionForJsp();

            //TODO make secure (this completely circumvents any security)
            SecureUser fromUser = HibernateUtil.getUserByName(fromUserName);
            SecureUser toUser = HibernateUtil.getUserByName(toUserName);
            Quantity fromUserBalance = fromUser.cashOnHand();
            Quantity amount = new Quantity(amt);

            Logger logger = Logger.getLogger(RPCHandler.class);

              if (fromUserName == null || fromUserName.equals("")) {
                  return "ERROR: No fromUserName provided -- " + fromUserName;
              } else if (toUserName == null || toUserName.equals("")) {
                  return "ERROR: No toUserName provided -- " + toUserName;
              } else if (amount.isNegative()) {
                  return "ERROR: Cash amount negative: " + amount;
              } else if (amount.isZero()) {
                  return "SUCCESS: Awarded 0 cash to " + toUserName;
              } else if (amount.compareTo(fromUserBalance) > 0) {
                  return "ERROR: Cash amount exceeds fromUser balance: " + fromUserName + " (" + fromUserBalance + "), " + amount;
              }

              String goal = "transfer cash from user " + fromUserName + " to user " + toUserName+ " in the amount of: " + amount;
              logger.info(GID.log() + "Attempting to " + goal);

              Funds funds = fromUser.provideCash(amount);
              toUser.receiveCash(funds);
              try {
                  transaction.commit();
              } catch (HibernateException e) {
                  transaction.rollback();
                  e.printStackTrace();
                  logger.error(e);
                  logger.warn("Failed to " + goal);
                  return "ERROR: Transfer Cash failed with HibernateException during commit -- " + amount;
              } finally {
                  HibernateUtil.currentSession().close();
              }

              logger.info(GID.log() + "Completed " + goal);

              String warnings = "";
              if (fromUser.hasWarnings() || toUser.hasWarnings()) {
                  warnings = ", with warnings: " + fromUser.getWarningsHTML() + " " + toUser.getWarningsHTML();
              }

              return "SUCCESS: Transferred " + amount + " cash from " + fromUserName + " to " + toUserName + warnings;
        }

        /**
         * Get current cash balance of the specified user
         * @param userName
         * @return cash balance of user, or null in case of error
         */
        public Quantity getCurrentBalance(String userName) {
            Logger logger = Logger.getLogger(RPCHandler.class);

            SecureUser user = HibernateUtil.getUserByName(userName);
            if (userName == null || userName.equals(""))
                return null;

            Quantity cashAmt = user.cashOnHand();
            logger.info(GID.log() + "Completed retrieval of balance for user " + userName + ", balance is " + cashAmt);
            return cashAmt;
        }
    }
}
