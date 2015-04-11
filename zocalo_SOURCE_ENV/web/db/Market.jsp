<html>
<head>
<title>The ICC WorldCup Prediction</title>
<!--
Copyright 2007-2009 Chris Hibbert.  All rights reserved.
Copyright 2005, 2006 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
<jsp:useBean id="markets" scope="session" class="net.commerce.zocalo.JspSupport.MarketDisplay" />
<jsp:setProperty name="markets" property="*" />
    <link rel="stylesheet" type="text/css" href="trading.css">
    <script type="text/javascript" src="display.js"></script>
</head>
<body bgcolor=linen>
<p align=center>
<img src="images/Logo/mainlogo1.gif">
</p><p align=right><a href="pages/Help.html" style="text-decoration: none">Click here for help</a></p>
<p align=center>
<font size = 4><b>Find the league which is presently active and click on it 
					to get started.</b></font>
</p>
<table width=100%><tr><td width=35%><img src=images/Logo/a.png width=250px height=500px></td><td width=65%>
<p align=center>
<% markets.beginTransaction(); %>
<% markets.processRequest(request, response); %>

<%= markets.navButtons() %>

<p align=right>
<font size=3 color=blue><b>Welcome, <%= markets.getUserName() %></b></font></p>
<p align=right>
<font size=4 color=red><%= markets.getCashBalanceDisplay() %></font>
</p>

<% if ( markets.marketsExist()) { %>
    <%= markets.getMarketNamesTable() %>
<% } else { %>
    <p> No markets defined yet.
<% } %>
<% markets.commitTransaction(); %>
</td>
</tr>
</table>

</body>
</html>
