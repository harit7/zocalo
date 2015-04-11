package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class createAccount_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<html>\r\n<head>\r\n<title>IWP-Create Account</title>\r\n<!--\r\nCopyright 2007-2009 Chris Hibbert.  All rights reserved.\r\n\r\nThis software is published under the terms of the MIT license, a copy\r\nof which has been included with this distribution in the LICENSE file.\r\n-->\r\n");
      net.commerce.zocalo.JspSupport.AccountCreationScreen createAccount = null;
      synchronized (session) {
        createAccount = (net.commerce.zocalo.JspSupport.AccountCreationScreen) _jspx_page_context.getAttribute("createAccount", PageContext.SESSION_SCOPE);
        if (createAccount == null){
          createAccount = new net.commerce.zocalo.JspSupport.AccountCreationScreen();
          _jspx_page_context.setAttribute("createAccount", createAccount, PageContext.SESSION_SCOPE);
        }
      }
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.introspect(_jspx_page_context.findAttribute("createAccount"), request);
      out.write("\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"trading.css\">\r\n    <script type=\"text/javascript\" src=\"display.js\"></script>\r\n</head>\r\n<body bgcolor=linen>\r\n<p align=\"center\">\r\n<img src=\"images/Logo/mainlogo1.gif\"><p align=right><a href=\"pages/Help.html\" style=\"text-decoration: none\">Click here for help</a></p>\r\n<br>\r\n<h2 align=\"center\">Create a new Account</h2>\r\n</p>\r\n\r\n<table width=100%><tr><td width=35%><img src=images/Logo/a.png width=250px height=500px></td><td width=65%>\r\n\r\n");
 createAccount.beginTransaction(); 
      out.write('\r');
      out.write('\n');
 createAccount.processRequest(request, response); 
      out.write("\r\n<p align=right>\r\n<form method=POST action=\"createAccount.jsp\">\r\n<table align=center>\r\n    <tr>\r\n        <td align=left><font size=4>Username: \t</font>:\r\n    </td><td>\r\n        <input type=text size=\"15\" name=userName value='");
      out.print( createAccount.getUserNameForWeb() );
      out.write("'><br>\r\n        </td>\r\n    </tr><tr>\r\n        <td align=left> <font size=4>Account Password: </font>:</td>\r\n        <td><input type=password size=\"15\" name=\"password\"></td>\r\n    </tr><tr>\r\n        <td align=left><font size=4>Retype Password: </font>\r\n        </td>\r\n        <td><input type=password size=\"15\" name=\"password2\"></td>\r\n    </tr><tr>\r\n        <td align=left> <font size=4>Email Address: </font><br> <small>(confirmation will be required) </small>:\r\n        </td>\r\n        <td>\r\n            <input type=text size=\"15\" name=\"emailAddress\" value=\"");
      out.print( createAccount.getEmailAddressForWeb() );
      out.write("\">\r\n        </td>\r\n    </tr><tr>\r\n        <td align=\"center\" colspan=5><input type=submit name=action value=\"Create Account\" align=center> </td>\r\n    </tr>\r\n</table>\r\n</form>\r\n</p>\r\n\r\n<br>\r\n\r\n<hr>\r\n<font size =3 color=blue><b>To activate your account, click on the activation link sent to your email.</b></font>\r\n<hr>\r\n ");
 if (createAccount.hasWarnings()) { 
      out.write("\r\n    <p align=\"center\" class=\"userMessage\">");
      out.print( createAccount.getWarnings() );
      out.write('\r');
      out.write('\n');
      out.write(' ');
 } 
      out.write("\r\n\r\n<br>\r\n\r\n");
 createAccount.commitTransaction(); 
      out.write("\r\n</td></tr></table>\r\n</body>\r\n</html>\r\n");
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
