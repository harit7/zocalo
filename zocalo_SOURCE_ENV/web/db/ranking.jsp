<html>
<head>
<jsp:useBean id="ranking" scope="request" class="net.commerce.zocalo.JspSupport.UserRanking" />
<jsp:setProperty name="ranking" property="*" />

<!--
Copyright 2007, 2009 Chris Hibbert.  All rights reserved.
Copyright 2006 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
    <link rel="stylesheet" type="text/css" href="trading.css">
    <script type="text/javascript" src="display.js"></script>
<title>User Ranking</title>
</head>
<body bgcolor=linen>
<p align=center>
<img src="images/Logo/mainlogo1.gif">
</p><p align=right><a href="pages/Help.html" style="text-decoration: none">Click here for help</a></p>
<table width=100%><tr><td width=35%><img src=images/Logo/a.png width=250px height=500px></td><td width=65%>
<% ranking.beginTransaction(); %>
<% ranking.processRequest(request, response); %>

<%= ranking.navButtons() %>
<p align=right><font size=3 color=blue>Welcome, <%= ranking.getUserName() %></font></p><br>
<p align="center">
<font size=6 color=blue><b><i>User Ranking</b></i></font>
</p>

<%= ranking.rankingTable(0) %>
<% ranking.commitTransaction(); %>
</p>
</td>
</tr></table>

</body>
</html>
