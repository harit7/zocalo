<html>
<head>
<jsp:useBean id="history" scope="request" class="net.commerce.zocalo.JspSupport.TradeHistory" />
<jsp:setProperty name="history" property="*" />

<!--
Copyright 2007, 2009 Chris Hibbert.  All rights reserved.
Copyright 2006 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
    <link rel="stylesheet" type="text/css" href="trading.css">
    <script type="text/javascript" src="display.js"></script>
<title><%= history.getUserName() %>-History</title>
</head>
<body bgcolor=linen>
<p align=center>
<img src="images/Logo/mainlogo1.gif">
</p><p align=right><a href="pages/Help.html" style="text-decoration: none">Click here for help</a></p>
<table width=100%><tr><td width=35%><img src=images/Logo/a.png width=250px height=500px></td><td width=65%>
<% history.beginTransaction(); %>
<% history.processRequest(request, response); %>

<%= history.navButtons() %>
<p align=right><font size=3 color=blue>Welcome, <%= history.getUserName() %></font></p><br>
<p align="center">
<font size=3 color=blue><b><i>Trading History</b></i></font>
</p>

<%= history.tradeTable() %>
<% history.commitTransaction(); %>
</p>
</td>
</tr></table>

</body>
</html>
