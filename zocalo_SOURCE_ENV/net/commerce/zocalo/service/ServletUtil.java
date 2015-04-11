package net.commerce.zocalo.service;

import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.NCSARequestLog;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.*;
import org.mortbay.util.MultiException;
import org.mortbay.servlet.CGI;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.IOException;

// Copyright 2007-2009 Chris Hibbert.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/** utility functionality for webservers based on servlets.  Particularly notice that the servletLock is
 used to ensure that the market code is non-reentrant.  */
public abstract class ServletUtil extends ServerUtil {
    private Server server;
    private HandlerCollection top;
    final static public Object servletLock = new Object();

    protected void initialLogContext() {
        ContextHandler logContext = new ContextHandler();
        logContext.setContextPath("/logs/*");
        logContext.setResourceBase("./logs/");
        logContext.addHandler(new ResourceHandler());

        NCSARequestLog log = new NCSARequestLog();
        log.setFilename("logs/yyyy_mm_dd.request.log");
        log.setRetainDays(30);
        log.setAppend(true);
        log.setExtended(true);

        RequestLogHandler rlHandler = new RequestLogHandler();
        rlHandler.setRequestLog(log);
        top.addHandler(rlHandler);
    }

    protected static Server createServerWithListener(String serverPort) {
        Server s = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(Integer.parseInt(serverPort));
        s.addConnector(connector);

        return s;
    }

    protected static void addServlet(ServletHandler handler, String servletName) {
        handler.addServletWithMapping("org.apache.jsp." + servletName + "_jsp", "/" + servletName + ".jsp");
        return;
    }

    protected void initializeHandlers(boolean installCGI) {
        // The Server delegates the request to its configured handler, which is normally an
        // instance of HandlerCollection.  The HandlerCollection calls each of its contained handlers
        // in turn.  Typically it is configured with a ContextHandlerCollection, a DefaultHandler and a
        // RequestLogHandler. This allows a request to be handled by a context or the default handler,
        // and then be passed to the request log handler.

        addContextHandlers(installCGI);
        top.addHandler(new DefaultHandler());
        initialLogContext();

        return;
    }

    protected ResourceHandler addResourceHandler(HandlerCollection handlers, String path, String base) {
        ContextHandler context = new ContextHandler(path);
        context.setContextPath(path);
        context.setResourceBase(base);
        ResourceHandler handler = new ResourceHandler();
        handler.setResourceBase(base);
        context.addHandler(handler);
        handlers.addHandler(context);
        return handler;
    }

    public void addContextHandlers(boolean installCGI) {
        ContextHandlerCollection handlers = new ContextHandlerCollection();

        addResourceHandler(handlers, "/images", "webpages/images");
        ResourceHandler handler = addResourceHandler(handlers, "/", "webpages");
        handler.setWelcomeFiles(new String[] { "index.html", "index.htm" });
        addExtraResourceHandlers(handlers);

//        Context other = new Context(handlers, "/yo", Context.SESSIONS);
//        other.addServlet(new ServletHolder(new HelloServlet("YO!")), "/*");

        if (installCGI) {
            Context cgi = new Context(handlers, "/cgi", Context.SESSIONS);
            cgi.setResourceBase("cgi");
            cgi.addServlet(CGI.class, "/");
        }

        top.addHandler(handlers);
        Context context = new Context(handlers, "/", Context.SESSIONS|Context.NO_SECURITY);
        addServlets(context);
    }

    protected void addExtraResourceHandlers(ContextHandlerCollection handlers) {
        // subclasses may override
    }

    abstract public void addServlets(Context context);

    protected void initializeServerTopLevel(String serverPort) {
        initializeServerTopLevel(serverPort, false);
    }

    protected void initializeServerTopLevelWithCGI(String serverPort) {
        initializeServerTopLevel(serverPort, true);
    }

    protected void initializeServerTopLevel(String serverPort, boolean installCGI) {
        server = createServerWithListener(serverPort);
        HandlerCollection wrapped = new HandlerCollection();
        top = wrapped;
        HandlerWrapper wrapper = new HandlerWrapper() {
            public void handle(String s, HttpServletRequest request, HttpServletResponse response, int i) throws IOException, ServletException {
                synchronized (servletLock) {
                    super.handle(s, request, response, i);
                }
            }
        };
        wrapper.addHandler(wrapped);
        server.addHandler(wrapper);
        initializeHandlers(installCGI);
        return;
    }

    protected static void runJettyServer(ServletUtil server) throws Exception {
        try {
            server.start ();
            server.finishInitialization();
        } catch (MultiException e) {
            e.printStackTrace();
        }
        server.join();
    }

    public void finishInitialization() {
        // subclasses can override
    }

    protected void join() throws InterruptedException {
        server.join();
    }

    protected void start() throws Exception {
        server.start();
    }
}
