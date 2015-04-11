package net.commerce.zocalo.service;

// Copyright 2006-2009 Chris Hibbert.  All rights reserved.
// Copyright 2005 CommerceNet Consortium, LLC.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

import net.commerce.zocalo.hibernate.HibernateUtil;
import net.commerce.zocalo.logging.Log4JInitializer;
import net.commerce.zocalo.rpc.RPCServer;
import net.commerce.zocalo.user.Registry;
import net.commerce.zocalo.JspSupport.*;
import net.commerce.zocalo.ajax.dispatch.*;
import net.commerce.zocalo.ajax.events.PriceAction;

import org.apache.log4j.Logger;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.cometd.BayeuxService;
import org.mortbay.cometd.AbstractBayeux;
import org.hibernate.HibernateException;

import java.util.*;
import java.sql.SQLException;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

/** Collection of markets and users, and main() entry point for running markets. */
public class AllMarkets extends CometServer {
    final static public String SERVER_PORT_KEY          = "server.port";
    final static public String ENABLE_RPC_SERVER        = "RPC.server.enable";
    final static public String DEFAULT_SERVER_PORT      = "80";
    final static public String RPC_SERVER_PORT          = "RPC.server.port";

    static public void main(String[] args) throws Exception {
        Properties props = readConfigFile();
        Log4JInitializer.initializeLog4J(props.getProperty("log.file"), "log4j.properties");
        Config.initializeConfiguration(props);
        Logger logger = Logger.getLogger(AllMarkets.class);

        String serverPort = props.getProperty(SERVER_PORT_KEY, DEFAULT_SERVER_PORT);
        AllMarkets markets = new AllMarkets(serverPort, props);
        if (serverPort.equals(DEFAULT_SERVER_PORT)) {
            logger.info(" opened port '" + serverPort + "'.  Browse to                    http://<<yourhost>>/Welcome.jsp");
        } else {
            logger.info(" opened port '" + serverPort + "'.  Browse to                    http://<<yourhost>>:" + serverPort + "/Welcome.jsp");
        }
        if (PropertyHelper.parseBoolean(ENABLE_RPC_SERVER, props, false)) {
            logger.warn("Enabling RPC server access.");
            runRPCServer(props.getProperty(RPC_SERVER_PORT));
        }

        runJettyServer(markets); //this is a blocking call
    }

    private static void runRPCServer(String portNumber) {
        RPCServer server = new RPCServer();
        server.run(portNumber);
    }

    public void addServlets(Context context) {
        ServletHandler handler = new ServletHandler();
        addServlet(handler, MarketDisplay.MARKET_NAME);
        addServlet(handler, ClaimPurchase.PURCHASE_CLAIM_NAME);
        addServlet(handler, ClaimPurchase.PURCHASE_COST_NAME);
        addServlet(handler, AccountDisplay.ACCOUNT_NAME);
        addServlet(handler, TradeHistory.HISTORY_NAME);
        addServlet(handler, WelcomeScreen.WELCOME_NAME);
        addServlet(handler, MarketCreation.CREATE_MARKETS_NAME);
        addServlet(handler, AccountCreationScreen.CREATE_ACCOUNT_NAME);
        addCometServletHandler(handler);
        context.setServletHandler(handler);
    }

    // called from tests
    public AllMarkets(String dbFilePath, boolean create) {
        String connectionURL = HibernateUtil.connectionUrl(dbFilePath, create);
        String mode = create ? HibernateUtil.SCHEMA_CREATE : HibernateUtil.SCHEMA_UPDATE;
        configure(connectionURL, mode);
    }

    // called from main() and WelcomeScreen.processRequest(...)
    public AllMarkets(String serverPort, Properties props) {
        initializeServerTopLevel(serverPort);

        configure(Config.getDefaultDBFile(), HibernateUtil.SCHEMA_UPDATE);
    }

    private void configure(String dbFileURL, String schemaUpdate) {
        Registry.initPasswdSeed(readConfigFile().getProperty(Config.ADMIN_PASSWORD_KEY));

        Logger trace = Logger.getLogger("trace");
        trace.info("   creating AllMarkets");
        initializeDB(dbFileURL, schemaUpdate, trace);
    }

    public static String channelName(String marketName) {
        addBayeuxService(marketName);
        return buildChannelName(marketName);
    }

    public static String buildChannelName(String marketName) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(marketName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encoded = marketName.replaceAll("[^a-zA-Z1-90/]+", "-");
        }

        return PriceChangeDispatcher.PRICE_CHANGE_TOPIC_URI + encoded;
    }

    private void initializeDB(String dbFileURL, String schemaUpdate, Logger trace) {
        HibernateUtil.initializeSessionFactory(dbFileURL, schemaUpdate);
        if (HibernateUtil.getUnowedMarket().size() != 0) {
            try {
                HibernateUtil.updateDB2007_5(dbFileURL);
            } catch (SQLException e) {
                trace.error("Unable to upgrade schema to 2007.5; please notify admin.", e);
            }
        }
        if (HibernateUtil.getMakerWithoutBeta().size() != 0) {
            try {
                HibernateUtil.updateDB2008_3(dbFileURL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (HibernateUtil.getMarketsSansOutcome().size() != 0) {
            HibernateUtil.updateDB2008_4(dbFileURL);
        }
        try {
            HibernateUtil.getPositionSansIndex();
        } catch (HibernateException e) {
            trace.error("Database not upgraded for 2009.1; please run migrate2009-1.sh or migrate2009-1.py.");
            System.exit(20091);
        } catch (NullPointerException npe) {
            trace.info("No Open Binary Markets found.");

        }
    }

    protected void startBayeuxService() {
        new MarketBayeuxService(BayeuxSingleton.getInstance().getBayeux());
    }

    protected static void addBayeuxService(String marketId) {
        if (! BayeuxSingleton.isSubscribed(marketId)) {
            new MarketBayeuxService(BayeuxSingleton.getInstance().getBayeux(), marketId);
            BayeuxSingleton.addSubscription(marketId);
        }
    }

    public static class MarketBayeuxService extends BayeuxService {
        public MarketBayeuxService(AbstractBayeux bayeux) {
            super(bayeux, "markets");

            PriceActionAppender.registerNewAppender(bayeux);
            TransitionAppender.registerNewAppender(bayeux);
        }

        public MarketBayeuxService(AbstractBayeux bayeux, String marketName) {
            super(bayeux, marketName);
            PriceChangeAppender.registerNewAppender(bayeux, marketName);
            NewChartAppender.registerNewAppender(bayeux, marketName);
        }
    }
}
