<html>
<head>
<jsp:useBean id="account" scope="request" class="net.commerce.zocalo.JspSupport.AccountDisplay" />
<jsp:setProperty name="account" property="*" />
<title>Welcome to the Marketplace!</title>
<!--
Copyright 2007 Chris Hibbert.  All rights reserved.
Copyright 2005 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
    <link rel="stylesheet" type="text/css" href="trading.css">
    <script type="text/javascript" src="display.js"></script>
    
    

<script>
window.location.hash="no-back-button";
window.location.hash="Again-No-back-button";//again because google chrome don't insert first hash into history
window.onhashchange=function(){window.location.hash="no-back-button";}
</script> 

    
</head>
<body bgcolor=linen>
<p align=center>
<image src="images/Logo/mainlogo1.gif" align=center></p><p align=right><a href="pages/Help.html" style="text-decoration: none">Click here for help</a></p>

<table width=100%><tr><td width=35%><img src=images/Logo/a.png width=250px height=500px></td><td width=65%>



<% account.beginTransaction(); %>
<% account.processRequest(request, response); %>

<%= account.navButtons("account.jsp") %>
<p align=center>
<font size=4 color=black><%= account.descriptionHtml() %></font>
 </p>

<% account.commitTransaction(); %>

</td></tr></table>


</body>
</html>
