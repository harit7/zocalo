package net.commerce.zocalo.service;

import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.cometd.continuation.ContinuationCometdServlet;
import org.mortbay.cometd.AbstractBayeux;

// Copyright 2008, 2009 Chris Hibbert.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/** sets up cometd connections for a Jetty web server. */
abstract public class CometServer extends ServletUtil {
    public ContinuationCometdServlet cometdServlet;

    public void addCometServletHandler(ServletHandler handler) {
        cometdServlet = new ContinuationCometdServlet();
        ServletHolder cometHolder = setupCometdServletHolder(cometdServlet);
        handler.addServletWithMapping(cometHolder, "/cometd/*");
    }

    private ServletHolder setupCometdServletHolder(ContinuationCometdServlet cometServlet) {
        ServletHolder cometHolder = new ServletHolder(cometServlet);
        cometHolder.setInitParameter("timeout", "240000");
        cometHolder.setInitParameter("interval", "0");
        cometHolder.setInitParameter("maxInterval", "30000");
        cometHolder.setInitParameter("multiFrameInterval", "1500");
        cometHolder.setInitParameter("JSONCommented", "true");
        cometHolder.setInitParameter("logLevel", "1");
        return cometHolder;
    }

    public void finishInitialization() {
        AbstractBayeux bayeux = cometdServlet.getBayeux();
        bayeux.setJSONCommented(true);
        BayeuxSingleton.getInstance().setBayeux(bayeux);
        startBayeuxService();
    }

    protected abstract void startBayeuxService();
}
