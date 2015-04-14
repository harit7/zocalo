<html>
<head>
<jsp:useBean id="login" scope="request"
	class="net.commerce.zocalo.JspSupport.WelcomeScreen" />
<jsp:setProperty name="login" property="*" />
<title>Welcome to ICC WorldCup Prediction</title>
<!--
Copyright 2007 Chris Hibbert.  All rights reserved.
Copyright 2006 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
-->
<style>
#side {
	border: 1px solid black;
}

body {
	background-size: cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
}
</style>
</head>
<body bgcolor="linen">
	<%
		login.beginTransaction();
	%>
	<%
		login.processRequest(request, response);
	%>

	<p align=center>
		<a href=http://www.csa.iisc.ernet.in / target="_blank"><img
			src=images/Logo/csa_logo.png width=150px height=70px></a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		<img src="images/Logo/mainlogo1.gif" width=750px height=70px> <a
			href="pages/Help.html" style="text-decoration: none">Click here
			for help</a>
	</p>


	<p align="center">
	<table border=0 width="100%">


		<tr align="left">
			<th rowspan="3" align="center" height="280"><img
				src="images/Logo/a.png" width="50%" height="200%" align="center"
				rowspan="2"></th>
			<td align="center" width="50%" id="side"><font size=5>Please
					login to enter the market: </font>
				<form method=POST action=Welcome.jsp>
					<table border=0 cellspacing=0 cellpadding=0 align="center">
						<tbody>
							<tr>
								<td><font size=4 color=red><b>Username:</b> </font></td>
							</tr>
							<tr>
								<td><input type=text name=userName><br></td>
							</tr>
							<tr>
								<td><font size=4 color=red><b> Password: </b> </font></td>
							</tr>
							<tr>
								<td><input type=password name=password><br> <input
									type=submit name=action value="Sign In"></td>
							</tr>
						</tbody>
					</table>
				</form> <%=login.getWarning()%> <%
 	login.commitTransaction();
 %></td>
		</tr>
		<tr>
			<td align="center"><font size=4> <a
					href="createAccount.jsp"> click here to create a new Account</a>.
			</font></td>
		</tr>
		<tr>
			<td align="center"><font size=4> <a
					href="resetPassword.jsp">Forgot password</a>.
			</font></td>
		</tr>
		<tr>
			<td><table border="0">
					<tr>
						<td><img src="images/Logo/ind.png" width="125px"
							height="125px" align="center"></td>
						<td align="center"><img src="images/Logo/eng.png"
							width="100px" height="125px" align="center"></td>
						<td><img src="images/Logo/Bangla.png" width="125px"
							height="125px" align="center"></td>
						<td><img src="images/Logo/Ireland.png" width="125px"
							height="125px" align="center"></td>
						<td><img src="images/Logo/SA.png" width="125px"
							height="125px" align="center"></td>
					</tr>
					<tr>
						<td><img src="images/Logo/SL.png" width="125px"
							height="125px" align="center"></td>
						<td><img src="images/Logo/pak.png" width="125px"
							height="125px" align="center"></td>
						<td><img src="images/Logo/nzl.png" width="125px"
							height="125px" align="center"></td>
						<td><img src="images/Logo/aus.png" width="125px"
							height="125px" align="center"></td>
						<td><img src="images/Logo/wi.gif" width="125px"
							height="125px" align="center"></td>
					</tr>
				</table></td>
		</tr>
	</table>
	<p align=center>
		<font size=5><b><a href="pages/Credits.html">Credits</a><br>

				<hr>
	</p>



</body>
</html>









