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
<body>

<%-- This section is visually a continuation of the preceding data provided in the parent frame. --%>

<% trader.processRequest(request, response); %>

<%=trader.pricesTable() %>

<hr noshade color="black">

<h3 align="center">Your Holdings</h3>
<table align=center border=1 cellspacing=0 cellpadding=3>
    <tr><td>Cash</td><td><%= trader.getBalanceMessage() %></td></tr>
    <tr><td><%= trader.getSharesLabel() %></td><td><%= trader.getHoldingsMessage() %></td></tr>
    <%= trader.getReservesRow() %>
</table>
<p>
<%= trader.showEarningsSummary() %>

<hr color="black">

<h3 align="center">Information</h3>

<table align=center border=0 cellspacing=0 cellpadding=5>
  <tr><td>
      <table align=center border=0 cellspacing=0 cellpadding=2>
          <tr><td align="center">Alert:</td></tr>
  <% if (trader.hasErrorMessages()) { %>
          <tr><td><span style="background-color:red;" id="traderRedMessage"><%= trader.getErrorMessages() %></span></td></tr>
  <% } else { %>
          <tr><td><span style="background-color:red;" id="traderRedMessage"></span></td></tr>
  <% } %>
      </table>
  </td></tr>
  <tr><td>
    <table align=center border=0 cellspacing=0 cellpadding=2>
        <tr><td align="center"><%= trader.getMessageLabel() %></td></tr>
            <tr><td align="center"><b><%= trader.getMessage() %></b></td></tr>
    </table>
  </td></tr>
  <tr><td>
    <table align=center border=0 cellspacing=0 cellpadding=2>
        <tr><td align="center"><%= trader.getCommonMessageLabel() %></td></tr>
            <tr><td><b><%= trader.getCommonMessages() %></b></td></tr>
    </table>
  </td></tr>
</table>


</body>
</html>
