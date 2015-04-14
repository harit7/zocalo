package org.apache.jsp;

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
      out.write("\n<title>Welcome to ICC WorldCup Prediction</title>\n<!--\nCopyright 2007 Chris Hibbert.  All rights reserved.\nCopyright 2006 CommerceNet Consortium, LLC.  All rights reserved.\n\nThis software is published under the terms of the MIT license, a copy\nof which has been included with this distribution in the LICENSE file.\n-->\n<style>\n#side {\n\tborder: 1px solid black;\n}\n\nbody {\n\tbackground-size: cover;\n\tbackground-repeat: no-repeat;\n\tbackground-attachment: fixed;\n}\n</style>\n</head>\n<body bgcolor=\"linen\">\n\t");

		login.beginTransaction();
	
      out.write('\n');
      out.write('	');

		login.processRequest(request, response);
	
      out.write("\n\n\t<p align=center>\n\t\t<a href=http://www.csa.iisc.ernet.in / target=\"_blank\"><img\n\t\t\tsrc=images/Logo/csa_logo.png width=150px height=70px></a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp\n\t\t<img src=\"images/Logo/mainlogo1.gif\" width=750px height=70px> <a\n\t\t\thref=\"pages/Help.html\" style=\"text-decoration: none\">Click here\n\t\t\tfor help</a>\n\t</p>\n\n\n\t<p align=\"center\">\n\t<table border=0 width=\"100%\">\n\n\n\t\t<tr align=\"left\">\n\t\t\t<th rowspan=\"3\" align=\"center\" height=\"280\"><img\n\t\t\t\tsrc=\"images/Logo/a.png\" width=\"50%\" height=\"200%\" align=\"center\"\n\t\t\t\trowspan=\"2\"></th>\n\t\t\t<td align=\"center\" width=\"50%\" id=\"side\"><font size=5>Please\n\t\t\t\t\tlogin to enter the market: </font>\n\t\t\t\t<form method=POST action=Welcome.jsp>\n\t\t\t\t\t<table border=0 cellspacing=0 cellpadding=0 align=\"center\">\n\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td><font size=4 color=red><b>Username:</b> </font></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td><input type=text name=userName><br></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td><font size=4 color=red><b> Password: </b> </font></td>\n\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td><input type=password name=password><br> <input\n\t\t\t\t\t\t\t\t\ttype=submit name=action value=\"Sign In\"></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</tbody>\n\t\t\t\t\t</table>\n\t\t\t\t</form> ");
      out.print(login.getWarning());
      out.write(' ');

 	login.commitTransaction();
 
      out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\"><font size=4> <a\n\t\t\t\t\thref=\"createAccount.jsp\"> click here to create a new Account</a>.\n\t\t\t</font></td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\"><font size=4> <a\n\t\t\t\t\thref=\"resetPassword.jsp\">Forgot password</a>.\n\t\t\t</font></td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td><table border=\"0\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td><img src=\"images/Logo/ind.png\" width=\"125px\"\n\t\t\t\t\t\t\theight=\"125px\" align=\"center\"></td>\n\t\t\t\t\t\t<td align=\"center\"><img src=\"images/Logo/eng.png\"\n\t\t\t\t\t\t\twidth=\"100px\" height=\"125px\" align=\"center\"></td>\n\t\t\t\t\t\t<td><img src=\"images/Logo/Bangla.png\" width=\"125px\"\n\t\t\t\t\t\t\theight=\"125px\" align=\"center\"></td>\n\t\t\t\t\t\t<td><img src=\"images/Logo/Ireland.png\" width=\"125px\"\n\t\t\t\t\t\t\theight=\"125px\" align=\"center\"></td>\n\t\t\t\t\t\t<td><img src=\"images/Logo/SA.png\" width=\"125px\"\n\t\t\t\t\t\t\theight=\"125px\" align=\"center\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td><img src=\"images/Logo/SL.png\" width=\"125px\"\n\t\t\t\t\t\t\theight=\"125px\" align=\"center\"></td>\n\t\t\t\t\t\t<td><img src=\"images/Logo/pak.png\" width=\"125px\"\n\t\t\t\t\t\t\theight=\"125px\" align=\"center\"></td>\n");
      out.write("\t\t\t\t\t\t<td><img src=\"images/Logo/nzl.png\" width=\"125px\"\n\t\t\t\t\t\t\theight=\"125px\" align=\"center\"></td>\n\t\t\t\t\t\t<td><img src=\"images/Logo/aus.png\" width=\"125px\"\n\t\t\t\t\t\t\theight=\"125px\" align=\"center\"></td>\n\t\t\t\t\t\t<td><img src=\"images/Logo/wi.gif\" width=\"125px\"\n\t\t\t\t\t\t\theight=\"125px\" align=\"center\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table></td>\n\t\t</tr>\n\t</table>\n\t<p align=center>\n\t\t<font size=5><b><a href=\"pages/Credits.html\">Credits</a><br>\n\n\t\t\t\t<hr>\n\t</p>\n\n\n\n</body>\n</html>\n\n\n\n\n\n\n\n\n\n");
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
