<html>
<head>
<title>IWP-Create Account</title>
<!--
Copyright 2007-2009 Chris Hibbert.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
<jsp:useBean id="createAccount" scope="session" class="net.commerce.zocalo.JspSupport.AccountCreationScreen" />
<jsp:setProperty name="createAccount" property="*" />
    <link rel="stylesheet" type="text/css" href="trading.css">
    <script type="text/javascript" src="display.js"></script>
</head>
<body bgcolor=linen>
<p align="center">
<img src="images/Logo/mainlogo1.gif"><p align=right><a href="pages/Help.html" style="text-decoration: none">Click here for help</a></p>
<br>
<h2 align="center">Create a new Account</h2>
</p>

<table width=100%><tr><td width=35%><img src=images/Logo/a.png width=250px height=500px></td><td width=65%>

<% createAccount.beginTransaction(); %>
<% createAccount.processRequest(request, response); %>
<p align=right>
<form method=POST action="createAccount.jsp">
<table align=center>
    <tr>
        <td align=left><font size=4>Username: 	</font>:
    </td><td>
        <input type=text size="15" name=userName value='<%= createAccount.getUserNameForWeb() %>'><br>
        </td>
    </tr><tr>
        <td align=left> <font size=4>Account Password: </font>:</td>
        <td><input type=password size="15" name="password"></td>
    </tr><tr>
        <td align=left><font size=4>Retype Password: </font>
        </td>
        <td><input type=password size="15" name="password2"></td>
    </tr><tr>
        <td align=left> <font size=4>Email Address: </font><br> <small>(confirmation will be required) </small>:
        </td>
        <td>
            <input type=text size="15" name="emailAddress" value="<%= createAccount.getEmailAddressForWeb() %>">
        </td>
    </tr><tr>
        <td align="center" colspan=5><input type=submit name=action value="Create Account" align=center> </td>
    </tr>
</table>
</form>
</p>

<br>

<hr>
<font size =3 color=blue><b>To activate your account, click on the activation link sent to your email.</b></font>
<hr>
 <% if (createAccount.hasWarnings()) { %>
    <p align="center" class="userMessage"><%= createAccount.getWarnings() %>
 <% } %>

<br>

<% createAccount.commitTransaction(); %>
</td></tr></table>
</body>
</html>
