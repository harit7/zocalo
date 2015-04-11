<html>
<head>
<jsp:useBean id="claim" scope="request" class="net.commerce.zocalo.JspSupport.ClaimPurchase" />
<jsp:setProperty name="claim" property="*" />
<title>Trading is now on for the <%= claim.getClaimName() %></title>
<!--
Copyright 2008, 2009 Chris Hibbert.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
    <link rel="stylesheet" type="text/css" href="trading.css">
    <script type="text/javascript" src="reactiveIssue.js"></script>
</head>
<body bgcolor=linen onLoad="onload_actions()" onunload="unsubscribeAll()">
<p align=center>
<img src="images/Logo/mainlogo1.gif">
</p><p align=right><a href="pages/Help.html" style="text-decoration: none">Click here for help</a></p>
<% claim.beginTransaction(); %>
<% claim.processRequest(request, response); %>
<p align=center><font size=3 color=blue><b><i><%= claim.navButtons() %>Welcome, <%= claim.getUserName() %>!</i></font></b></p>.

<br>
<h1 align="center"><%= claim.displayClaimName() %></h1>
<table cellPadding=0>
    <tr align=right><td bgcolor="dadbec">
        <p class="claimDescription-wide"><%= claim.getClaimDescription() %></p>
        <%= claim.buyOrEditClaimHtml() %>
        <% if (claim.getUser().hasWarnings() ) { %>
            <p class="userMessage"><%= claim.getUser().getWarningsHTML() %>
        <% } %>
        <% System.out.println ("rohith: claim.marketHasBookOrders()" + claim.marketHasBookOrders());
         if (claim.marketHasBookOrders()) { %>
            <td width="100px" valign="middle">
                <table align=center bgcolor=#EEEEEE cellpadding=5>
                    <tr>
                        <%= claim.displayBestOrdersHtml() %>
                    </tr>
                </table>
             </td>
        <% } %>
    <td align=center>
        <% if (claim.marketHasBookOrders() ) { %>
        <img  src="<%= claim.historyChartNameForJsp() %>" width="<%= claim.getChartSize() %>" height="<%= claim.getChartSize() %>" id="PriceChart" alt="price chart">
        <% } else { %>
        <img align=right src="<%= claim.multiChartNameForJsp() %>" width="<%= claim.getChartSize() %>" id="PriceChart" height="<%= claim.getChartSize() %>" alt="price chart">
        <% } %>
    </td></tr>
</table>
<br>
<table align=center cellPadding=0 width="90%">
    <tr><td valign="top">
        <% if (claim.marketHasBookOrders() ) { %>
            <br><%= claim.claimDeletionFormHtml() %>
        <% } %>
<!--        <center>Your available balance is $<%= claim.cashOnHandHtml() %></center><p><br> -->
<!--        <center>Your available balance is &#x20b9<%= claim.cashOnHandHtml() %></center><p><br> -->
	        <center>Your available balance is Rs. <%= claim.cashOnHandHtml() %></center><p><br> 
        <%= claim.displayHoldingsHtml() %>
    </td><td valign="top">
        <%= claim.displayTradeHistory() %>
    </td></tr>
</table>
<% claim.commitTransaction(); %>

<script type="text/javascript" src="display.js"></script>
<script type="text/javascript" src="tradingUI.js"></script>
<script type="text/javascript" src="../dojo/dojo/dojo.js"></script>
<script type="text/javascript">
    dojo.require("dojox.cometd");
    dojo.require("dojo.fx");
</script>

<script type="text/javascript">
    function onload_actions() {
        dojox.cometd.init('/cometd');

        var priceUpdateChannel = "<%= claim.priceUpdateChannel() %>";
        var newChartChannel = "/newChart/";
        var transitionTopic = "/transition";
        dojox.cometd.startBatch();
         channels.push(dojox.cometd.subscribe(transitionTopic,onTransitionMessage));
         channels.push(dojox.cometd.subscribe(priceUpdateChannel, onPriceUpdate));
         channels.push(dojox.cometd.subscribe(newChartChannel + encode("<%= claim.getClaimName() %>"), onNewChartMessage));
        dojox.cometd.endBatch();
        refreshImage(document.getElementById("PriceChart"));
    }
    function onNewChartMessage(msg) {
        refreshImage(document.getElementById("PriceChart"));
    }
    function onPriceUpdate(msg) {
        var rowSel = document.getElementById('rowSelection');
        if (rowSel !== null) {  // is this a multiMarket cost-limit page?
            market.handlePriceUpdate(msg.data);
        } else {
            updateMMPrices(msg.data);
        }
    }
    function onTransitionMessage(msg) {
        var event = msg.data;
        addNewMarketLink(document.URL, event);
    }
</script>

</body>
</html>
