package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class ranking_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      net.commerce.zocalo.JspSupport.UserRanking ranking = null;
      synchronized (request) {
        ranking = (net.commerce.zocalo.JspSupport.UserRanking) _jspx_page_context.getAttribute("ranking", PageContext.REQUEST_SCOPE);
        if (ranking == null){
          ranking = new net.commerce.zocalo.JspSupport.UserRanking();
          _jspx_page_context.setAttribute("ranking", ranking, PageContext.REQUEST_SCOPE);
        }
      }
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.introspect(_jspx_page_context.findAttribute("ranking"), request);
      out.write("\n\n<!--\nCopyright 2007, 2009 Chris Hibbert.  All rights reserved.\nCopyright 2006 CommerceNet Consortium, LLC.  All rights reserved.\n\nThis software is published under the terms of the MIT license, a copy\nof which has been included with this distribution in the LICENSE file.\n-->\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"trading.css\">\n    <script type=\"text/javascript\" src=\"display.js\"></script>\n<title>User Ranking</title>\n</head>\n<body bgcolor=linen>\n<p align=center>\n<img src=\"images/Logo/mainlogo1.gif\">\n</p><p align=right><a href=\"pages/Help.html\" style=\"text-decoration: none\">Click here for help</a></p>\n<table width=100%><tr><td width=35%><img src=images/Logo/a.png width=250px height=500px></td><td width=65%>\n");
 ranking.beginTransaction(); 
      out.write('\n');
 ranking.processRequest(request, response); 
      out.write('\n');
      out.write('\n');
      out.print( ranking.navButtons() );
      out.write("\n<p align=right><font size=3 color=blue>Welcome, ");
      out.print( ranking.getUserName() );
      out.write("</font></p><br>\n<p align=\"center\">\n<font size=6 color=blue><b><i>User Ranking</b></i></font>\n</p>\n\n");
      out.print( ranking.rankingTable(0) );
      out.write('\n');
 ranking.commitTransaction(); 
      out.write("\n</p>\n</td>\n</tr></table>\n\n</body>\n</html>\n");
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
