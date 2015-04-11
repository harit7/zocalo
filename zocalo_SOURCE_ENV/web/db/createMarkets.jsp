<html>
<head>
<title>Zocalo: Create New Markets</title>
<!--
Copyright 2006-2009 Chris Hibbert.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
<jsp:useBean id="createMarkets" scope="session" class="net.commerce.zocalo.JspSupport.MarketCreation" />
<jsp:setProperty name="createMarkets" property="*" />
    <link rel="stylesheet" type="text/css" href="trading.css">
    <script type="text/javascript" src="display.js"></script>
</head>
<body>

<% createMarkets.beginTransaction(); %>
<% createMarkets.processRequest(request, response); %>

<%= createMarkets.navButtons() %>

<p>
<h2 align="center">Logged in as <%= createMarkets.getUserName() %></h2>
<p>

<form method=POST action="createMarkets.jsp">
<table border=0 bgcolor="lightgreen">
    <tr>
        <td>
            Create a new Market named: <input type=text name= marketName >
        </td>
        <td>
            Endow the Market Maker with: <input type=text name="marketMakerEndowment">
        </td>
    </tr>
    <tr> <td colspan=2> &nbsp; </td> </tr>
    <tr> <td colspan=2>
        <label><input type=radio name='outcomes' checked value='binary' onchange="toggleVisibility('binary');" >
        Market will be a Binary Market (two outcomes, <b>Yes</b> and <b>No</b>.)</label></td> </tr>
    <tr > <td>
        <label><input type=radio name='outcomes'         value='multi'  onchange="toggleVisibility('binary');">
		   Market will have multiple outcomes. </label><br>
	  <div id='binary' style='display:none;background:lightgray;' align="center">
          Separate outcome names with commas.<br>
        <textarea rows="3" cols="40" name='positions'>yes,no,maybe</textarea></div>
    </td></tr>
    <tr> <td colspan=2> &nbsp; </td> </tr>
    <tr>
        <td colspan=2 align=center>
            <input type=submit class='smallFontButton' name=action value="Submit"></td>
    </tr>
</table>
<input type=hidden name=userName value="<%= createMarkets.getUserName() %>" >
</form>

     <% if (createMarkets.getUser().hasWarnings() ) { %>
        <p align="center" class="userMessage"><%= createMarkets.getUser().getWarningsHTML() %>
     <% } %>

<% createMarkets.commitTransaction(); %>

<p>

</body>
</html>
