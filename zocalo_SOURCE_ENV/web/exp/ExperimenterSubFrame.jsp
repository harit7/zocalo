<html>
<head>
<jsp:useBean id="experimenter"  scope="request" class="net.commerce.zocalo.JspSupport.ExperimenterScreen" />
<jsp:setProperty name="experimenter" property="*" />
<title>Experiment Management subFrame</title>
<!--
Copyright 2007-2009 Chris Hibbert.  All rights reserved.
Copyright 2005 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
<meta http-equiv="refresh" content=15>
</head>
<body onLoad="onload_actions();">
<script type="text/javascript" src="experiment.js"></script>
<script type="text/javascript">
    function onload_actions() {
        top.chart.updateScale();
    }
</script>

<% experimenter.processRequest(request, response); %>

<% if (experimenter.currentRound().equals("")) { %>
    <table align="center" border=1 cellspacing=0 cellpadding=0>
      <tr><td colspan=2><span style="color: red; ">Session not initialized</span></td></tr>
    </table>
<% } else { %>
    <table align="center" border=1 cellspacing=0 cellpadding=0>
      <tr><td align="center"><strong><%= experimenter.getCommonMessageLabel() %></strong></td></tr>
      <tr><td><%= experimenter.getCommonMessages() %></td></tr>
    </table>
    <p>
    <table align="center" border=0 cellspacing=5 cellpadding=0>
      <tr><td> Current <%= experimenter.roundLabel() %>:</td><td><%= experimenter.currentRound() %></td></tr>
    </table>
<% } %>

<% if (!experimenter.getErrorMessage().equals("")) { %>
    <table align="center" border=0 cellspacing=5 cellpadding=0>
      <tr><td><span style="color: red; "><%= experimenter.getErrorMessage() %></span></td></tr>
    </table>
<% } %>

<p><br>

<%= experimenter.displayButtons() %>

<% if (!experimenter.currentRound().equals("")) { %>
    <h2 align="center">Scores</h2>
    <%=experimenter.getScoresHtml() %>
<% } %>
<%=experimenter.stateSpecificDisplay() %>

<p><br><p>

<h2>Configure experiment from file:</h2>
<FORM ENCTYPE="multipart/form-data" method="POST" action="ExperimenterSubFrame.jsp">
<input type="hidden" name="action" value="loadFile">
<INPUT TYPE="file" NAME="filename">
<INPUT TYPE="submit" VALUE="upload">
</FORM>

<p>
<h3>Experiment Results</h3>
<%=experimenter.linkForLogFile() %>
<%= experimenter.scaleDiv() %>

</body>
</html>
