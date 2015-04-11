package org.apache.jsp.pm;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class purchaseCost_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.Vector _jspx_dependants;

  public java.util.List getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<html>\n<head>\n");
      net.commerce.zocalo.JspSupport.ClaimPurchase claim = null;
      synchronized (request) {
        claim = (net.commerce.zocalo.JspSupport.ClaimPurchase) _jspx_page_context.getAttribute("claim", PageContext.REQUEST_SCOPE);
        if (claim == null){
          claim = new net.commerce.zocalo.JspSupport.ClaimPurchase();
          _jspx_page_context.setAttribute("claim", claim, PageContext.REQUEST_SCOPE);
        }
      }
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.introspect(_jspx_page_context.findAttribute("claim"), request);
      out.write("\n<title>Trading is now on for the ");
      out.print( claim.getClaimName() );
      out.write("</title>\n<!--\nCopyright 2008, 2009 Chris Hibbert.  All rights reserved.\n\nThis software is published under the terms of the MIT license, a copy\nof which has been included with this distribution in the LICENSE file.\n-->\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"trading.css\">\n    <script type=\"text/javascript\" src=\"reactiveIssue.js\"></script>\n</head>\n<body bgcolor=linen onLoad=\"onload_actions()\" onunload=\"unsubscribeAll()\">\n<p align=center>\n<img src=\"images/Logo/mainlogo1.gif\">\n</p><p align=right><a href=\"pages/Help.html\" style=\"text-decoration: none\">Click here for help</a></p>\n");
 claim.beginTransaction(); 
      out.write('\n');
 claim.processRequest(request, response); 
      out.write("\n<p align=center><font size=3 color=blue><b><i>");
      out.print( claim.navButtons() );
      out.write("Welcome, ");
      out.print( claim.getUserName() );
      out.write("!</i></font></b></p>.\n\n<br>\n<h1 align=\"center\">");
      out.print( claim.displayClaimName() );
      out.write("</h1>\n<table cellPadding=0>\n    <tr align=right><td bgcolor=\"dadbec\">\n        <p class=\"claimDescription-wide\">");
      out.print( claim.getClaimDescription() );
      out.write("</p>\n        ");
      out.print( claim.buyOrEditClaimHtml() );
      out.write("\n        ");
 if (claim.getUser().hasWarnings() ) { 
      out.write("\n            <p class=\"userMessage\">");
      out.print( claim.getUser().getWarningsHTML() );
      out.write("\n        ");
 } 
      out.write("\n        ");
 System.out.println ("rohith: claim.marketHasBookOrders()" + claim.marketHasBookOrders());
         if (claim.marketHasBookOrders()) { 
      out.write("\n            <td width=\"100px\" valign=\"middle\">\n                <table align=center bgcolor=#EEEEEE cellpadding=5>\n                    <tr>\n                        ");
      out.print( claim.displayBestOrdersHtml() );
      out.write("\n                    </tr>\n                </table>\n             </td>\n        ");
 } 
      out.write("\n    <td align=center>\n        ");
 if (claim.marketHasBookOrders() ) { 
      out.write("\n        <img  src=\"");
      out.print( claim.historyChartNameForJsp() );
      out.write("\" width=\"");
      out.print( claim.getChartSize() );
      out.write("\" height=\"");
      out.print( claim.getChartSize() );
      out.write("\" id=\"PriceChart\" alt=\"price chart\">\n        ");
 } else { 
      out.write("\n        <img align=right src=\"");
      out.print( claim.multiChartNameForJsp() );
      out.write("\" width=\"");
      out.print( claim.getChartSize() );
      out.write("\" id=\"PriceChart\" height=\"");
      out.print( claim.getChartSize() );
      out.write("\" alt=\"price chart\">\n        ");
 } 
      out.write("\n    </td></tr>\n</table>\n<br>\n<table align=center cellPadding=0 width=\"90%\">\n    <tr><td valign=\"top\">\n        ");
 if (claim.marketHasBookOrders() ) { 
      out.write("\n            <br>");
      out.print( claim.claimDeletionFormHtml() );
      out.write("\n        ");
 } 
      out.write("\n<!--        <center>Your available balance is $");
      out.print( claim.cashOnHandHtml() );
      out.write("</center><p><br> -->\n<!--        <center>Your available balance is &#x20b9");
      out.print( claim.cashOnHandHtml() );
      out.write("</center><p><br> -->\n\t        <center>Your available balance is Rs. ");
      out.print( claim.cashOnHandHtml() );
      out.write("</center><p><br> \n        ");
      out.print( claim.displayHoldingsHtml() );
      out.write("\n    </td><td valign=\"top\">\n        ");
      out.print( claim.displayTradeHistory() );
      out.write("\n    </td></tr>\n</table>\n");
 claim.commitTransaction(); 
      out.write("\n\n<script type=\"text/javascript\" src=\"display.js\"></script>\n<script type=\"text/javascript\" src=\"tradingUI.js\"></script>\n<script type=\"text/javascript\" src=\"../dojo/dojo/dojo.js\"></script>\n<script type=\"text/javascript\">\n    dojo.require(\"dojox.cometd\");\n    dojo.require(\"dojo.fx\");\n</script>\n\n<script type=\"text/javascript\">\n    function onload_actions() {\n        dojox.cometd.init('/cometd');\n\n        var priceUpdateChannel = \"");
      out.print( claim.priceUpdateChannel() );
      out.write("\";\n        var newChartChannel = \"/newChart/\";\n        var transitionTopic = \"/transition\";\n        dojox.cometd.startBatch();\n         channels.push(dojox.cometd.subscribe(transitionTopic,onTransitionMessage));\n         channels.push(dojox.cometd.subscribe(priceUpdateChannel, onPriceUpdate));\n         channels.push(dojox.cometd.subscribe(newChartChannel + encode(\"");
      out.print( claim.getClaimName() );
      out.write("\"), onNewChartMessage));\n        dojox.cometd.endBatch();\n        refreshImage(document.getElementById(\"PriceChart\"));\n    }\n    function onNewChartMessage(msg) {\n        refreshImage(document.getElementById(\"PriceChart\"));\n    }\n    function onPriceUpdate(msg) {\n        var rowSel = document.getElementById('rowSelection');\n        if (rowSel !== null) {  // is this a multiMarket cost-limit page?\n            market.handlePriceUpdate(msg.data);\n        } else {\n            updateMMPrices(msg.data);\n        }\n    }\n    function onTransitionMessage(msg) {\n        var event = msg.data;\n        addNewMarketLink(document.URL, event);\n    }\n</script>\n\n</body>\n</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
