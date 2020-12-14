<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="Objects.Books"%>

<DOCTYPE ! html>
<html>
<%
	if (session.getAttribute("userName") == null) {
	response.sendRedirect("Login.jsp");
	return;
}
%>
<%
	ArrayList<Books> bookInfo = (ArrayList) request.getAttribute("myBookInfo");
if (bookInfo == null) {
	response.sendRedirect("ViewMyBook");
	return;
}
%>
<body>
	<% 
	if(bookInfo.size()!=0)
	{%>
	<h1>Your Purchased Books</h1>
	<table>
		<tr>
			<th>S No</th>
			<th>Book Name</th>
			<th>Author Name</th>
			<th>Year</th>
		</tr>
		<%
			int i = 1;
		for (Books k : bookInfo) {
		%>
		<tr>
			<td><%=i++%></td>
			<td><%=k.getName()%></td>
			<td><%=k.getAuthor()%></td>
			<td><%=k.getYear()%></td>
		</tr>
		<%}
		%>
	</table>
	<%}
	%>
</body>
</html>