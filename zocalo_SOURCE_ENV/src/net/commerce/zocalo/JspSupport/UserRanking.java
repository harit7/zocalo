package net.commerce.zocalo.JspSupport;

import net.commerce.zocalo.service.MarketOwner;
import net.commerce.zocalo.hibernate.HibernateUtil;
import net.commerce.zocalo.html.HtmlSimpleElement;
import net.commerce.zocalo.NumberDisplay;
import net.commerce.zocalo.html.HtmlTable;
import net.commerce.zocalo.html.HtmlRow;
import net.commerce.zocalo.ajax.events.Trade;
import net.commerce.zocalo.user.SecureUser;
import net.commerce.zocalo.user.UserRank;
import net.commerce.zocalo.claim.Position;

import org.jfree.data.time.TimePeriodValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// Copyright 2007, 2009 Chris Hibbert.  All rights reserved.
// Copyright 2006 CommerceNet Consortium, LLC.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/** TradeHistory displays trading history in a tabular form. */
public class UserRanking extends UserPage {
    static final public String RANKING_JSP = "ranking.jsp";
    static final public String RANKING_NAME = "ranking";
    private SecureUser currentUser;
    static final public SimpleDateFormat datePrinter = new SimpleDateFormat("MM/dd/yy HH:mm z");
    public static final String PAGE_TITLE = "User Ranking";
    
    public static final int PAGE_SIZE   = 100000;

    public UserRanking() {
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        currentUser = MarketOwner.registryLookup(request);
        if (currentUser == null) {
            redirectResult(request, response);
        }
        setUserName(currentUser.getName());
        detectAdminCookie(request);
    }

    public String rankingTable(int pageNo) {
        List<UserRank> ranks;
        try {
            ranks = HibernateUtil.getUserRanking(0, PAGE_SIZE);
        } catch (Exception e) {
            ranks = null;
        }

        StringBuffer buff = new StringBuffer();
        if (ranks != null) {
            HtmlTable.start(buff, "lightblue", new String[] { "#Rank", "User Name", "Balance<br> (In Rupees)"}, "orderTable");
            for (UserRank rank: ranks) {
                
         
                printRow(buff, rank);
            }
            HtmlTable.endTag(buff);
        }
        return buff.toString();
    }

    static public void printRow(StringBuffer buff, UserRank rank) {
        
     

        HtmlRow.startTag(buff);
  
      
        buff.append(HtmlSimpleElement.printTableCell(""+rank.getRank()));
        
        buff.append(HtmlSimpleElement.printTableCell(rank.getUserName()));
        
        buff.append(HtmlSimpleElement.printTableCell(rank.getBalance().printAsDollars()));
        HtmlRow.endTag(buff);
    }

    public String getRequestURL(HttpServletRequest request) {
        if (currentUser == null) {
            return WelcomeScreen.WELCOME_JSP;
        } else {
            return null;
        }
    }

    public SecureUser getUser() {
        return currentUser;
    }

    public String navButtons() {
        return navButtons(RANKING_JSP);
    }
}
