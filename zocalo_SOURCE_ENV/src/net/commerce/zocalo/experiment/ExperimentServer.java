package net.commerce.zocalo.experiment;

import net.commerce.zocalo.service.CometServer;
import net.commerce.zocalo.service.BayeuxSingleton;
import net.commerce.zocalo.ajax.dispatch.TradeEventDispatcher;
import net.commerce.zocalo.ajax.dispatch.TransitionDispatcher;
import net.commerce.zocalo.ajax.dispatch.BidUpdateDispatcher;
import net.commerce.zocalo.ajax.dispatch.PrivateEventDispatcher;
import net.commerce.zocalo.ajax.events.LimitTrade;

import org.mortbay.cometd.BayeuxService;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.apache.log4j.Logger;
import dojox.cometd.*;

import java.util.Map;
import java.util.HashMap;

// Copyright 2007-2009 Chris Hibbert.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/** Attempt at a super class for Experiment sessions; not currently in use.  */
public class ExperimentServer extends CometServer {

    private ExperimentServer(String serverPort) {
        initializeServerTopLevelWithCGI(serverPort);
    }

    static public void main (String[] args) throws Exception {
        String serverPort = SessionSingleton.getServerPort();
        ExperimentServer server = new ExperimentServer(serverPort);

        Logger logger = Logger.getLogger("Session Config");
        if (serverPort == "80" || serverPort == "") {
            logger.info(" opened port '" + serverPort + "'.  Browse to                  http://<<yourhost>>/Experimenter.jsp");
        } else {
            logger.info(" opened port '" + serverPort + "'.  Browse to                  http://<<yourhost>>:" + serverPort + "/Experimenter.jsp");
        }
        runJettyServer(server);
    }

    public void addServlets(Context context) {
        ServletHandler handler = new ServletHandler();
        addServlet(handler, "Experimenter");
        addServlet(handler, "ExperimenterSubFrame");
        addServlet(handler, "JudgeSubSubFrame");
        addServlet(handler, "JudgeSubFrame");
        addServlet(handler, "Judge");
        addServlet(handler, "TraderSubSubFrame");
        addServlet(handler, "TraderSubFrame");
        addServlet(handler, "Trader");
        addServlet(handler, "Login");

        addCometServletHandler(handler);
        context.setServletHandler(handler);
    }

    protected void startBayeuxService() {
        new ExperimentBayeuxService(BayeuxSingleton.getInstance().getBayeux());
    }

    protected void addExtraResourceHandlers(ContextHandlerCollection handlers) {
        addResourceHandler(handlers, "/logs", "logs");
    }

    public static class ExperimentBayeuxService extends BayeuxService {
        static final private Map<String, String> clientMap = new HashMap<String, String>();

        public ExperimentBayeuxService(Bayeux bayeux) {
            super(bayeux, "experiments");
            subscribe(PrivateEventDispatcher.PRIVATE_EVENT_TOPIC_SUFFIX, "sendPrivateUpdate");
        }

        public Object sendPrivateUpdate(Client client, Object obj) {
            Map<String, Object> data = (Map<String, Object>) obj;
            if (Boolean.TRUE.equals(data.get("join"))) {
                String s = (String)data.get("user");
                clientMap.put(s, client.getId());

                client.addListener(new RemoveListener() {
                    public void removed(String clientId, boolean timeout) {
                        clientMap.values().remove(clientId);
                    }
                });
            }
            return obj;
        }

        public static String getClientId(String name) {
            return clientMap.get(name);
        }
    }
}
