<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html>
<html>
<%
	if(session.getAttribute("adminName")==null)
	{
		response.sendRedirect("AdminLogin.jsp");
	}
%>
<body>
	<center>
	<form action="insertBooks" method="post">
	Enter Book Name:<input type="text" name="bookname"\><br><br>
	Enter Book AuthorName:<input type="text" name="authorname"\><br><br>
	Enter Book Year:<input type="text" name="bookyear"\><br><br>
	Enter Number Of Books:<input type="text" name="numberofbooks"\><br><br>
	<button>Submit</button>
	</form>
	</center>
	</body>
</html>