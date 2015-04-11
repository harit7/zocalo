package org.apache.jsp.pm;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class createMarkets_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<html>\n<head>\n<title>Zocalo: Create New Markets</title>\n<!--\nCopyright 2006-2009 Chris Hibbert.  All rights reserved.\n\nThis software is published under the terms of the MIT license, a copy\nof which has been included with this distribution in the LICENSE file.\n-->\n");
      net.commerce.zocalo.JspSupport.MarketCreation createMarkets = null;
      synchronized (session) {
        createMarkets = (net.commerce.zocalo.JspSupport.MarketCreation) _jspx_page_context.getAttribute("createMarkets", PageContext.SESSION_SCOPE);
        if (createMarkets == null){
          createMarkets = new net.commerce.zocalo.JspSupport.MarketCreation();
          _jspx_page_context.setAttribute("createMarkets", createMarkets, PageContext.SESSION_SCOPE);
        }
      }
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.introspect(_jspx_page_context.findAttribute("createMarkets"), request);
      out.write("\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"trading.css\">\n    <script type=\"text/javascript\" src=\"display.js\"></script>\n</head>\n<body>\n\n");
 createMarkets.beginTransaction(); 
      out.write('\n');
 createMarkets.processRequest(request, response); 
      out.write('\n');
      out.write('\n');
      out.print( createMarkets.navButtons() );
      out.write("\n\n<p>\n<h2 align=\"center\">Logged in as ");
      out.print( createMarkets.getUserName() );
      out.write("</h2>\n<p>\n\n<form method=POST action=\"createMarkets.jsp\">\n<table border=0 bgcolor=\"lightgreen\">\n    <tr>\n        <td>\n            Create a new Market named: <input type=text name= marketName >\n        </td>\n        <td>\n            Endow the Market Maker with: <input type=text name=\"marketMakerEndowment\">\n        </td>\n    </tr>\n    <tr> <td colspan=2> &nbsp; </td> </tr>\n    <tr> <td colspan=2>\n        <label><input type=radio name='outcomes' checked value='binary' onchange=\"toggleVisibility('binary');\" >\n        Market will be a Binary Market (two outcomes, <b>Yes</b> and <b>No</b>.)</label></td> </tr>\n    <tr > <td>\n        <label><input type=radio name='outcomes'         value='multi'  onchange=\"toggleVisibility('binary');\">\n\t\t   Market will have multiple outcomes. </label><br>\n\t  <div id='binary' style='display:none;background:lightgray;' align=\"center\">\n          Separate outcome names with commas.<br>\n        <textarea rows=\"3\" cols=\"40\" name='positions'>yes,no,maybe</textarea></div>\n    </td></tr>\n    <tr> <td colspan=2> &nbsp; </td> </tr>\n");
      out.write("    <tr>\n        <td colspan=2 align=center>\n            <input type=submit class='smallFontButton' name=action value=\"Submit\"></td>\n    </tr>\n</table>\n<input type=hidden name=userName value=\"");
      out.print( createMarkets.getUserName() );
      out.write("\" >\n</form>\n\n     ");
 if (createMarkets.getUser().hasWarnings() ) { 
      out.write("\n        <p align=\"center\" class=\"userMessage\">");
      out.print( createMarkets.getUser().getWarningsHTML() );
      out.write("\n     ");
 } 
      out.write('\n');
      out.write('\n');
 createMarkets.commitTransaction(); 
      out.write("\n\n<p>\n\n</body>\n</html>\n");
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
