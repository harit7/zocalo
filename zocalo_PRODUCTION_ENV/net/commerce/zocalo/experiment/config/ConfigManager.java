package net.commerce.zocalo.experiment.config;

import net.commerce.zocalo.service.AllMarkets;
import net.commerce.zocalo.service.ServletUtil;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHandler;

import java.util.Properties;
// Copyright 2008, 2009 Chris Hibbert.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/** Main entry point for interactively editing experiment configurations.  */
public class ConfigManager extends ServletUtil {
    public ConfigManager() {
        Properties props = readConfigFile();

        String serverPort = props.getProperty(AllMarkets.SERVER_PORT_KEY, AllMarkets.DEFAULT_SERVER_PORT);
        initializeServerTopLevel(serverPort);
        initializeHandlers(false);
    }

    public void addServlets(Context context) {
        ServletHandler handler = new ServletHandler();
        addServlet(handler, ExperimentConfig.CONFIG_PAGE);
        context.setServletHandler(handler);
    }

    static public void main (String[] args) throws Exception {
        ConfigManager server = new ConfigManager();

        runJettyServer(server);
    }

}
