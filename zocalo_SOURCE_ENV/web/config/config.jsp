<html>
<head>
<jsp:useBean id="conf" scope="request" class="net.commerce.zocalo.experiment.config.ConfigEditor" />
<jsp:setProperty name="conf" property="*" />
<title>Editing Exeriment Configurations</title>
<!--
Copyright 2008 Chris Hibbert.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
    <script type="text/javascript" src="support.js"></script>
</head>
<body bgcolor="BBEEDD">
<% conf.processRequest(request, response); %>

<table border=0 cellspacing=0 cellpadding=50 width="90%" > <tbody>
  <tr> <td align="center">
        <img src="images/logo.zocalo.jpg" height=81 width=250 align="top" >
       </td>
  </tr>
  <tr> <td align="center">
           <%= conf.renderProperties() %>
       </td>
  </tr>
</tbody></table>



</body>
</html>
