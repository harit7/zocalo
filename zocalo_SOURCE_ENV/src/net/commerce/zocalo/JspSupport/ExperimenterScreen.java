package net.commerce.zocalo.JspSupport;

import net.commerce.zocalo.experiment.*;
import net.commerce.zocalo.html.HtmlSimpleElement;
import net.commerce.zocalo.html.HtmlTable;
import net.commerce.zocalo.html.HtmlRow;
import net.commerce.zocalo.market.Market;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

// Copyright 2007-2009 Chris Hibbert.  All rights reserved.
//  Copyright 2005 CommerceNet Consortium, LLC.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/** Jsp support for Experimenter's screen in experiments.  */
public class ExperimenterScreen extends ExperimentPage {
    final static public String FILENAME_FIELD = "filename";
    final static public String DISPLAY_SCORES_ACTION = "Display scores";
    final static public String STOP_VOTING_ACTION = "Stop Voting";

    private String action;
    private String filename;
    private String message = "";

    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        Session session = SessionSingleton.getSession();
        try {
            if (null == action) {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                if (isMultipart) {
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List items = upload.parseRequest(request);
                    for (Iterator iterator = items.iterator(); iterator.hasNext();) {
                        FileItem fileItem = (FileItem) iterator.next();
                        if (fileItem.getFieldName().equals(FILENAME_FIELD)) {
                            SessionSingleton.setSession(processStream(fileItem.getInputStream()));
                        }
                    }
                }
            } else if (session == null) {
                return;
            } else if (session.startRoundActionLabel().equalsIgnoreCase(action)) {
                session.startNextTimedRound();
            } else if (DISPLAY_SCORES_ACTION.equals(action)) {
                if (session instanceof JudgingSession) {
                    JudgingSession jSession = (JudgingSession)session;
                    jSession.endScoringPhase();
                    message = "";
                } else {
                    message = "judging not enabled.";
                }
            } else if (STOP_VOTING_ACTION.equals(action)) {
                if (session instanceof VotingSession) {
                    VotingSession vSession = (VotingSession)session;
                    vSession.endVoting();
                    message = "";
                } else {
                    message = "judging not enabled.";
                }
            } else if (action != null && action.startsWith(session.stopRoundActionLabel())) {
                session.endTrading(true);
            }
        } catch (ScoreException e) {
            message = "unable to calculate scores.  Have the judges entered estimates?";
        } catch (IOException e) {
            return;
        } catch (Exception e) {
            return;
        }
        if (request != null && "POST".equals(request.getMethod())) {
            redirectResult(request, response);
        }
    }

    private Properties processStream(InputStream inputStream) throws IOException {
        Properties props = new Properties();
        props.load(inputStream);
        return props;
    }

    public Market getMarket(String claimName) {
        Session session = SessionSingleton.getSession();
        return(session.getMarket());
     }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getScoresHtml() {
        Session session = SessionSingleton.getSession();
        if (session == null) {
            return "";
        }
        Iterator iterator = session.playerNameSortedIterator();
        StringBuffer buff = new StringBuffer();
        HtmlTable.start(buff, scoreTableColumnLabels("Player"));
        while (iterator.hasNext()) {
            String playerName = (String) iterator.next();
            HtmlRow.startTag(buff);
            buff.append(HtmlSimpleElement.printTableCell(playerName));
            buff.append(scoresAsHtml(session.getPlayer(playerName), session.rounds()));
            HtmlRow.endTag(buff);
        }
        HtmlTable.endTagWithP(buff);
        return buff.toString();
    }

    public String getRequestURL(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    public String displayButtons() {
        Session session = SessionSingleton.getSession();
        if (session == null) {
            return "";
        }
        return stateSpecificButtons(session.experimenterButtons(), "ExperimenterSubFrame.jsp");
    }

    // called for experimenter's state transition buttons
    static private String stateSpecificButtons(String[] buttons, String targetPage) {
        StringBuffer buff = new StringBuffer();
        buff.append("   <table align=\"center\" width=\"100%\" border=0 cellspacing=6>\n");
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null) {
                inputForm(buff, buttons[i], targetPage);
            }
        }
        buff.append("   </tr>\n   </table>\n");
        return buff.toString();
    }

    static private void inputForm(StringBuffer buff, String roundAction, String targetPage) {
        buff.append("\n\t<td>");
        buff.append(HtmlSimpleElement.formHeaderWithPost(targetPage).toString());
        buff.append(HtmlSimpleElement.submitInputField(roundAction));
        buff.append("\n\t\t</form>\n\t</td>\n");
    }


    public String getErrorMessage() {
        Session session = SessionSingleton.getSession();
        if (session == null) {
            return message;
        } else {
            return message + "<br>" + session.getErrorMessage();
        }
    }

    public void warn(String s) {
        message = message + "<br>" + s;
    }

    public String linkForLogFile() {
        Session session = SessionSingleton.getSession();
        if (session == null) {
            return "no active log.";
        }
        StringBuffer buff = new StringBuffer();
        PrintStringAdaptor printer = new PrintStringAdaptor() {
            public void printString(StringBuffer buff, String s) {
                printLogFileLinks(buff, s);
            }
        };
        session.logFileLinks(buff, printer);
        return buff.toString();
    }

    public String stateSpecificDisplay() {
        Session session = SessionSingleton.getSession();
        if (session != null) {
            return session.stateSpecificDisplay();
        }
        return "";
    }

    public static void printLogFileLinks(StringBuffer b, String linkableName) {
        String urlName = linkableName.replace('\\', '/');
        b.append("Download raw ").append(HtmlSimpleElement.link("logfile", "../" + urlName));
        b.append("<br>Download processed ").append(HtmlSimpleElement.link("logfile", "cgi/logToCSV.pl?" + urlName));
        b.append("<br>View as ").append(HtmlSimpleElement.link("table", "cgi/logToHtml.pl?" + urlName));
    }
}
