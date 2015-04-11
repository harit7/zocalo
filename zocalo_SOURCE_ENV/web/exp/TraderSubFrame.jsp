<html>
<head>
<jsp:useBean id="trader"  scope="request" class="net.commerce.zocalo.JspSupport.TraderScreen" />
<jsp:setProperty name="trader" property="*" />
<title> <%= trader.getClaimName() %> </title>
<!--
Copyright 2007-2009 Chris Hibbert.  All rights reserved.
Copyright 2005 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
</head>
<body onLoad="onload_actions();">

<% trader.processRequest(request, response); %>

<h3 align="center">Orders</h3>

<table align='center' bgcolor="DADBEC" border=0  cellpadding=8 cellspacing="3">
<%=trader.claimPurchaseFormRow() %>

<%=trader.marketOrderFormRow() %>
</table>

<br>
<iframe width="390" height="580" style="border: 0" name="traderDynamicFrame" id="traderDynamicFrame"
    src="TraderSubSubFrame.jsp?userName=<%= trader.getUserName() %>">
</iframe>
<%= trader.scaleDiv() %>

<script type="text/javascript" src="experiment.js"></script>
<script type="text/javascript" src="traderUI.js"></script>
<script type="text/javascript" src="../dojo/dojo/dojo.js"></script>
<script type="text/javascript">
    function onload_actions() {
        dojo.require("dojox.cometd");
        var reloadableSubSubframe = getNamedIFrame('traderDynamicFrame');
        var reloadableSubframe = getNamedIFrame('subFrame');

        top.reloadMySubSubframe = function() {
            reloadableSubSubframe.location.replace(reloadableSubSubframe.location.href);
        };

        top.reloadMySubframe = function() {
            reloadableSubframe.location.replace(reloadableSubframe.location.href);
        };

        top.startRoundActions = function() {
            top.reloadMySubframe();
        };

        top.transitionAction = function() {
            top.reloadMySubframe();
        };

        top.updateBestBuyPrice = function(newValue) {
            updateInputValue('sellMarketOrderForm', 'price', newValue);
        };

        top.updateBestSellPrice = function(newValue) {
            updateInputValue('buyMarketOrderForm', 'price', newValue);
        };

        function onPrivateMessage(msg) {
            var event = msg.data;
            var repaidDebt = event.repaidDebt;
            if (repaidDebt !== "" && repaidDebt !== undefined && repaidDebt.length > 0) {
                top.reloadMySubframe();
                var span = reloadableSubSubframe.document.getElementById("traderRedMessage");
                span.innerHTML = "Assets have been liquidated.  Trading is no longer disabled.";
            }
        }
        dojox.cometd.init('/cometd');
        dojox.cometd.subscribe(privateTopic,onPrivateMessage);

        var userName = "<%= trader.getUserName() %>";
        if (userName !== "") {
            dojox.cometd.publish(privateTopic, { user: userName, join: true });
        }
    }
</script>

</body>
</html>
