package org.apache.jsp.pm;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class Welcome_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      net.commerce.zocalo.JspSupport.WelcomeScreen login = null;
      synchronized (request) {
        login = (net.commerce.zocalo.JspSupport.WelcomeScreen) _jspx_page_context.getAttribute("login", PageContext.REQUEST_SCOPE);
        if (login == null){
          login = new net.commerce.zocalo.JspSupport.WelcomeScreen();
          _jspx_page_context.setAttribute("login", login, PageContext.REQUEST_SCOPE);
        }
      }
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.introspect(_jspx_page_context.findAttribute("login"), request);
      out.write("\n<title>Welcome to ICC WorldCup Prediction </title>\n<!--\nCopyright 2007 Chris Hibbert.  All rights reserved.\nCopyright 2006 CommerceNet Consortium, LLC.  All rights reserved.\n\nThis software is published under the terms of the MIT license, a copy\nof which has been included with this distribution in the LICENSE file.\n-->\n<style>\n #side{ border:1px solid black;}body{ background-size: cover; background-repeat: no-repeat; background-attachment: fixed;}</style>\n</head>\n<body bgcolor=\"linen\">\n");
 login.beginTransaction(); 
      out.write('\n');
 login.processRequest(request, response); 
      out.write("\n\n<p align=center>\n<a href=http://www.csa.iisc.ernet.in/ target=\"_blank\"><img src=images/Logo/csa_logo.png width=150px height=70px></a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp\n<img src=\"images/Logo/mainlogo1.gif\" width=750px height=70px>\n<a href=\"pages/Help.html\" style=\"text-decoration: none\">Click here for help</a>\n</p>\n \n\n  <p align=\"center\">\n  <table border=0 width=\"100%\">\n             \n\n        <tr align=\"left\"><th rowspan=\"3\" align=\"center\" height=\"280\" >\n <img src=\"images/Logo/a.png\" width=\"50%\" height=\"200%\"  align=\"center\" rowspan=\"2\"></th>\n  <td align=\"center\" width=\"50%\" id=\"side\"> \n      <font size = 5>Please login to enter the market: </font> \n<form method=GET action=Welcome.jsp>\n<table border=0 cellspacing=0 cellpadding=0 align=\"center\"> <tbody>\n<tr><td>\n<font size = 4 color = red><b>Username:</b> </font>\n</td></tr>\n<tr> <td>\n<input type=text name=userName ><br>\n            </td></tr>\n<tr><td>\n<font size = 4 color = red><b> Password: </b> </font>\n</td></tr>\n<tr><td>\n <input type=password name=password ><br>\n");
      out.write("            <input type=submit name=action value=\"Sign In\">\n          </td>\n</tr>\n      </tbody></table>\n </form>\n  ");
      out.print( login.getWarning() );
      out.write('\n');
 login.commitTransaction(); 
      out.write("\n  </td>\n</tr>\n <tr><td align=\"center\"><font size=4> <a href=\"createAccount.jsp\"> click here to create a new Account</a>.</font></td>\n    </tr>\n<tr> <td><table  border=\"0\">\t<tr> <td> <img src=\"images/Logo/ind.png\" width=\"125px\" height=\"125px\"  align=\"center\"></td><td align=\"center\"><img src=\"images/Logo/eng.png\" width=\"100px\" height=\"125px\"  align=\"center\"></td><td> <img src=\"images/Logo/Bangla.png\" width=\"125px\" height=\"125px\"  align=\"center\"></td><td><img src=\"images/Logo/Ireland.png\" width=\"125px\" height=\"125px\"  align=\"center\"></td><td><img src=\"images/Logo/SA.png\" width=\"125px\" height=\"125px\"  align=\"center\"></td></tr><tr><td><img src=\"images/Logo/SL.png\" width=\"125px\" height=\"125px\"  align=\"center\"></td><td><img src=\"images/Logo/pak.png\" width=\"125px\" height=\"125px\"  align=\"center\"></td><td><img src=\"images/Logo/nzl.png\" width=\"125px\" height=\"125px\"  align=\"center\"></td><td><img src=\"images/Logo/aus.png\" width=\"125px\" height=\"125px\"  align=\"center\"></td><td><img src=\"images/Logo/wi.gif\" width=\"125px\" height=\"125px\"  align=\"center\"></td></tr></table></td> </tr></table>\n");
      out.write("<p align=center>\n<font size=5><b><a href=\"pages/Credits.html\">Credits</a><br> \t\n\n<hr>\n</p>\n\n\n\n</body>\n</html>\n  \n  \n  \n  \n  \n  \n  \n  \n ");
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
