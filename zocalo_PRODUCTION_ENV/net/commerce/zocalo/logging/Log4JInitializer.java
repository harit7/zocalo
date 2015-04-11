package net.commerce.zocalo.logging;

// Copyright 2009 Chris Hibbert.  All rights reserved.
// Copyright 2005 CommerceNet Consortium, LLC.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

import org.apache.log4j.*;

import java.io.File;
import java.io.IOException;

/** Initialize the Log4J logging subsystem.  */
public class Log4JInitializer {
    final static public String UserError = "UserError";
    final static public String LOG_FILE_DIR = "logs";
    final static private Layout layout = new PatternLayout("%d{MM/dd hh:mm:ss.SSS/zzz} %6p - %12c{1} - %m%n");
    static private boolean initializedLog4J = false;

    static public FileAppender initializeLog4J(String title, String logProps) {
        if (! initializedLog4J) {
            LogManager.resetConfiguration();
            PropertyConfigurator.configure(logProps);
            initializedLog4J = true;
        }

        if (null == title) {
            return null;
        }
        return openLogFile(title.trim());
    }

    static private FileAppender openLogFile(String title) {
        File logFile = null;
        FileAppender fileAppender = null;
        try {
            logFile = File.createTempFile(title + "-", ".log", new File(LOG_FILE_DIR));
            fileAppender = new FileAppender(layout, logFile.getAbsolutePath());
        } catch (IOException e) {
            Logger  logger = Logger.getLogger("NoLogFile");
            logger.error("Couldn't create a log file", e);
        }
        if (fileAppender != null) {
            BasicConfigurator.configure(fileAppender);
        }
        return fileAppender;
    }
}
