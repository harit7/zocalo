package org.apache.jsp.pm;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class purchaseClaim_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n<title>Claim Details ");
      out.print( claim.getClaimName() );
      out.write("</title>\n<!--\nCopyright 2007-2009 Chris Hibbert.  All rights reserved.\nCopyright 2005 CommerceNet Consortium, LLC.  All rights reserved.\n\nThis software is published under the terms of the MIT license, a copy\nof which has been included with this distribution in the LICENSE file.\n-->\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"trading.css\">\n</head>\n<body onLoad=\"onload_actions()\" onunload=\"unsubscribeAll()\">\n\n");
 claim.beginTransaction(); 
      out.write('\n');
 claim.processRequest(request, response); 
      out.write('\n');
      out.write('\n');
      out.print( claim.navButtons() );
      out.write("  Welcome, ");
      out.print( claim.getUserName() );
      out.write(".\n\n<br>\n<h1 align=\"center\">");
      out.print( claim.displayClaimName() );
      out.write("</h1>\n        <p class=\"claimDescription\">");
      out.print( claim.getClaimDescription() );
      out.write("\n<p>\n");
 if (claim.marketHasBookOrders() ) { 
      out.write("\n    <table align=center cellPadding=0>\n        <tr><td colspan=3><img align=left src=\"images/blank.gif\" width=\"650\" height=\"1\" alt=\"spacer\"></td>\n        </tr>\n        <tr><td width=20%>\n                <img align=left src=\"");
      out.print( claim.historyChartNameForJsp() );
      out.write("\" width=\"");
      out.print( claim.getChartSize() );
      out.write("\" height=\"");
      out.print( claim.getChartSize() );
      out.write("\" id=\"PriceChart\" alt=\"price chart\">\n        </td>\n        ");
 if (claim.isOpen()) { 
      out.write("\n            <td valign=\"middle\" width=110>\n                <table align=left bgcolor=#EEEEEE cellpadding=5>\n                    <tr><td align='center'>\n                        <table>\n                            <tr>\n                                ");
      out.print( claim.displayBestOrdersHtml() );
      out.write("\n                            </tr>\n                        </table>\n                    </td></tr>\n                </table>\n             </td>\n            ");
 } 
      out.write("\n        <td>\n             ");
      out.print( claim.buyOrEditClaimHtml() );
      out.write("\n             ");
 if (claim.getUser().hasWarnings() ) { 
      out.write("\n                        <p class=\"userMessage\">");
      out.print( claim.getUser().getWarningsHTML() );
      out.write("\n             ");
 } 
      out.write("\n         </td></tr>\n     </table>\n");
 } else { 
      out.write("\n    <table align=center width=90% cellPadding=0>\n        <tr><td width=20%>\n            <img align=left src=\"");
      out.print( claim.multiChartNameForJsp() );
      out.write("\" width=\"");
      out.print( claim.getChartSize() );
      out.write("\" height=\"");
      out.print( claim.getChartSize() );
      out.write("\" id=\"PriceChart\" alt=\"price chart\">\n        </td><td>\n            ");
      out.print( claim.displayBestOrdersHtml() );
      out.write("\n        </td><td>\n             ");
      out.print( claim.buyOrEditClaimHtml() );
      out.write("\n             ");
 if (claim.getUser().hasWarnings() ) { 
      out.write("\n                    <p class=\"userMessage\">");
      out.print( claim.getUser().getWarningsHTML() );
      out.write("\n             ");
 } 
      out.write("\n         </td></tr>\n     </table>\n");
 } 
      out.write("\n\n<table border=0 align=center width=90% cellPadding=1>\n    <tr><td colspan=2>\n        <img align=left src=\"images/blank.gif\" width=\"650\" height=\"1\" alt=\"spacer\"></td>\n    </tr>\n    <tr><td valign=\"top\">\n        <center>Cash: ");
      out.print( claim.cashOnHandHtml() );
      out.write("</center><br>\n        ");
 if (claim.marketHasBookOrders() ) { 
      out.write("\n            ");
      out.print( claim.claimDeletionFormHtml() );
      out.write("\n        ");
 } 
      out.write("\n        <center>");
      out.print( claim.displayHoldingsHtml() );
      out.write("</center>\n    </td><td align=\"top\">\n        ");
      out.print( claim.displayTradeHistory() );
      out.write("\n    </td></tr>\n</table>\n");
 claim.commitTransaction(); 
      out.write("\n\n<script type=\"text/javascript\" src=\"tradingUI.js\"></script>\n<script type=\"text/javascript\" src=\"display.js\"></script>\n\n<script type=\"text/javascript\" src=\"../dojo/dojo/dojo.js\"></script>\n<script type=\"text/javascript\">\n    dojo.require(\"dojox.cometd\");\n    dojo.require(\"dojo.fx\");\n</script>\n\n<script type=\"text/javascript\">\n    function onload_actions() {\n        dojox.cometd.init('/cometd');\n        var priceUpdateChannel = \"");
      out.print( claim.priceUpdateChannel() );
      out.write("\";\n        var newChartChannel = \"/newChart/\";\n        var transitionTopic = \"/transition\";\n        dojox.cometd.startBatch();\n         channels.push(dojox.cometd.subscribe(transitionTopic,onTransitionMessage));\n         channels.push(dojox.cometd.subscribe(priceUpdateChannel, onPriceUpdate));\n         channels.push(dojox.cometd.subscribe(newChartChannel + \"");
      out.print( claim.getClaimName() );
      out.write("\", onNewChartMessage));\n        dojox.cometd.endBatch();\n        refreshImage(document.getElementById(\"PriceChart\"));\n    }\n    function onNewChartMessage(msg) {\n        refreshImage(document.getElementById(\"PriceChart\"));\n    }\n    function onPriceUpdate(msg) {\n        updateMMPrices(msg.data);\n    }\n    function onTransitionMessage(msg) {\n        var event = msg.data;\n        addNewMarketLink(document.URL, event);\n    }\n</script>\n<hr>\n<p align=center>\n<font size=5><b><a href=\"pages/Credits.html\">Credits</a><br> \t\n<a href=\"pages/Help.html\">Click here for help</a>\n<hr>\n</p>\n\n</body>\n</html>\n");
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
