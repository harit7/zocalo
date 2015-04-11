<html>
<head>
<jsp:useBean id="claim" scope="request" class="net.commerce.zocalo.JspSupport.ClaimPurchase" />
<jsp:setProperty name="claim" property="*" />
<title>Claim Details <%= claim.getClaimName() %></title>
<!--
Copyright 2007-2009 Chris Hibbert.  All rights reserved.
Copyright 2005 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
    <link rel="stylesheet" type="text/css" href="trading.css">
</head>
<body onLoad="onload_actions()" onunload="unsubscribeAll()">

<% claim.beginTransaction(); %>
<% claim.processRequest(request, response); %>

<%= claim.navButtons() %>  Welcome, <%= claim.getUserName() %>.

<br>
<h1 align="center"><%= claim.displayClaimName() %></h1>
        <p class="claimDescription"><%= claim.getClaimDescription() %>
<p>
<% if (claim.marketHasBookOrders() ) { %>
    <table align=center cellPadding=0>
        <tr><td colspan=3><img align=left src="images/blank.gif" width="650" height="1" alt="spacer"></td>
        </tr>
        <tr><td width=20%>
                <img align=left src="<%= claim.historyChartNameForJsp() %>" width="<%= claim.getChartSize() %>" height="<%= claim.getChartSize() %>" id="PriceChart" alt="price chart">
        </td>
        <% if (claim.isOpen()) { %>
            <td valign="middle" width=110>
                <table align=left bgcolor=#EEEEEE cellpadding=5>
                    <tr><td align='center'>
                        <table>
                            <tr>
                                <%= claim.displayBestOrdersHtml() %>
                            </tr>
                        </table>
                    </td></tr>
                </table>
             </td>
            <% } %>
        <td>
             <%= claim.buyOrEditClaimHtml() %>
             <% if (claim.getUser().hasWarnings() ) { %>
                        <p class="userMessage"><%= claim.getUser().getWarningsHTML() %>
             <% } %>
         </td></tr>
     </table>
<% } else { %>
    <table align=center width=90% cellPadding=0>
        <tr><td width=20%>
            <img align=left src="<%= claim.multiChartNameForJsp() %>" width="<%= claim.getChartSize() %>" height="<%= claim.getChartSize() %>" id="PriceChart" alt="price chart">
        </td><td>
            <%= claim.displayBestOrdersHtml() %>
        </td><td>
             <%= claim.buyOrEditClaimHtml() %>
             <% if (claim.getUser().hasWarnings() ) { %>
                    <p class="userMessage"><%= claim.getUser().getWarningsHTML() %>
             <% } %>
         </td></tr>
     </table>
<% } %>

<table border=0 align=center width=90% cellPadding=1>
    <tr><td colspan=2>
        <img align=left src="images/blank.gif" width="650" height="1" alt="spacer"></td>
    </tr>
    <tr><td valign="top">
        <center>Cash: <%= claim.cashOnHandHtml() %></center><br>
        <% if (claim.marketHasBookOrders() ) { %>
            <%= claim.claimDeletionFormHtml() %>
        <% } %>
        <center><%= claim.displayHoldingsHtml() %></center>
    </td><td align="top">
        <%= claim.displayTradeHistory() %>
    </td></tr>
</table>
<% claim.commitTransaction(); %>

<script type="text/javascript" src="tradingUI.js"></script>
<script type="text/javascript" src="display.js"></script>

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
         channels.push(dojox.cometd.subscribe(newChartChannel + "<%= claim.getClaimName() %>", onNewChartMessage));
        dojox.cometd.endBatch();
        refreshImage(document.getElementById("PriceChart"));
    }
    function onNewChartMessage(msg) {
        refreshImage(document.getElementById("PriceChart"));
    }
    function onPriceUpdate(msg) {
        updateMMPrices(msg.data);
    }
    function onTransitionMessage(msg) {
        var event = msg.data;
        addNewMarketLink(document.URL, event);
    }
</script>
<hr>
<p align=center>
<font size=5><b><a href="pages/Credits.html">Credits</a><br> 	
<a href="pages/Help.html">Click here for help</a>
<hr>
</p>

</body>
</html>
