<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html>
<html>
<title>User Login</title>
<%
		if (session.getAttribute("userName") != null) {
			response.sendRedirect("home.jsp");
		}
%>
<body>
	<center>
	<h1>User Login</h1>
	 <form name="loginForm" method="POST" action="j_security_check">
            <p>User name: <input type="text" name="j_username"/></p>
            <p>Password: <input type="password"  name="j_password"/></p>
            <p>  <input type="submit" value="Submit"/></p>
        </form>       
	</center>
</body>
</html>