<html>
<head>
<jsp:useBean id="ranking" scope="request" class="net.commerce.zocalo.JspSupport.UserRanking" />
<jsp:setProperty name="ranking" property="*" />
<title>User Ranking</title>
<!--
Copyright 2007, 2009 Chris Hibbert.  All rights reserved.
Copyright 2006 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
    <link rel="stylesheet" type="text/css" href="trading.css">
    <script type="text/javascript" src="display.js"></script>
</head>
<body>
<p align=center>
<img src="images/Logo/createAccountLogo.gif">
</p>
<% ranking.beginTransaction(); %>
<% ranking.processRequest(request, response); %>

<%= ranking.navButtons() %>

<p align="center">
<font size=7 color=blue><b><i>User Ranking</b></i></font>
</p>

<%=ranking.rankingTable(0) %> 
<% ranking.commitTransaction(); %>
<hr>
<p align=center>
<font size=5><b><a href="pages/Credits.html">Credits</a><br> 	
<a href="pages/Help.html">Click here for help</a>
<hr>
</p>
</body>
</html>
