<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html>
<html>
<%
	if(session.getAttribute("adminName")!=null)
	{
		response.sendRedirect("AdminActions.jsp");
		return;
	}
%>
<body>
	<center>
	<form action="adminLogin" method="post">
	AdminUserName:<input type="text" name="username"\><br><br>
	AdiminPassword:<input type="text" name="password"\><br><br>
	<button>Submit</button>
	</form>
	</center>
	</body>
</html>