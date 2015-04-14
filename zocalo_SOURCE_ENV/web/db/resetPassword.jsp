<%@page import="net.commerce.zocalo.JspSupport.ResetPasswordScreen"%>
<html>
<head>
<title>Reset Password</title>
<!--
Copyright 2007-2009 Chris Hibbert.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
<jsp:useBean id="resetPassword" scope="session" class="net.commerce.zocalo.JspSupport.ResetPasswordScreen" />
<jsp:setProperty name="resetPassword" property="*" />
    <link rel="stylesheet" type="text/css" href="trading.css">
    <script type="text/javascript" src="display.js"></script>
</head>
<body bgcolor=linen>
<p align="center">
<img src="images/Logo/mainlogo1.gif"><p align=right><a href="pages/Help.html" style="text-decoration: none">Click here for help</a></p>
<br>
<h2 align="center">Reset Password</h2>
</p>

<table width=100%><tr><td width=35%><img src=images/Logo/a.png width=250px height=500px></td><td width=65%>

<% resetPassword.beginTransaction(); %>
<% resetPassword.processRequest(request, response);
   String status = resetPassword.getStatus();
   String action = "";
 
%>
<p align=right>
<form method=POST action="resetPassword.jsp">
<table align=center>
    <% 
     
    if(status == null || status.equals(ResetPasswordScreen.STATUS_GET_EMAIL))
    {
    	out.println(
    	"<tr>"+
       		 "<td align=left> <font size=4>Email Address: </font><br> <small>(reset link will be sent here) </small>:"+
        	"</td>"+
       	 	"<td>"+
            	"<input type=text size=\"15\" name=\"emailAddress\" value=\"\">"+
      	  	"</td>"+
    	"</tr>");
    	action = ResetPasswordScreen.ACTION_SEND_EMAIL;
    	
    	
    }
    else if(status != null && status.equals(ResetPasswordScreen.STATUS_GET_PASSWORD))
    {
    	String reqId  = resetPassword.getResetReqId();
    	out.println(" <tr>"+
    	        "<td align=left> <font size=4>Account Password: </font>:</td>"+
    	        "<td><input type=password size='15' name='password'></td>"+
    	    "</tr><tr>"+
    	        "<td align=left><font size=4>Retype Password: </font>"+
    	        "</td>"+
    	        "<td><input type=password size='15' name='password2'></td>"+
    	    "</tr> "+"<tr><td><input type = hidden name = 'resetReqId' value ="+ (reqId==null?"": reqId) +" ></td></tr>");
    	action = ResetPasswordScreen.ACTION_RESET_PASSWORD;
    	
    }	
	
	%>
	<tr>
    	<td align = left ><input type= hidden value= <%=action %> name = "action" > </td>
    </tr>
    <tr>
        <td align="center" colspan=5><input type=submit name=action value="Submit" align=center> </td>
    </tr>
    <% resetPassword.clear(); %> 
</table>
</form>
</p>

<br>




 <% if (resetPassword.hasWarnings()) { %>
 <hr>
    <p align="center" class="userMessage"><%= resetPassword.getWarnings() %>
 <hr>
 <% } %>

<br>

<% resetPassword.commitTransaction(); %>
</td></tr></table>
</body>
</html>
