<%@ page import="java.util.*, java.io.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="Objects.Groups" %>

<!DOCTYPE html>
<html>
<%
	if(session.getAttribute("userName")==null)
	{
		response.sendRedirect("Login.jsp");
		return;
	}
%>
<body>
	<form action="createGroup" method="post">
	Enter Your groupName to Create:<input type="text" name="groupName"/ >
	<input type="submit" value="submit"/>
	</form>
	
</body>
</html>