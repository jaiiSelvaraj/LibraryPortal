<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="Objects.Books"%>

<!DOCTYPE html>
<html>
<%
	if (session.getAttribute("userName") == null) {
	response.sendRedirect("Login.jsp");
	return;
}
%>
<%
ArrayList<Books> listOfBooks = (ArrayList) request.getAttribute("searchBookList");
%>
<body>

	<form action="search" method="post">
		searchBook:<input type="text" name="book" /> 
		<input type="submit"  value="submit">
	</form><br><br>
	<%if(listOfBooks!=null) {%>
	<h2>Search Result:</h2>
	<table>
		<tr>
			<th>S No</th>
			<th>Book Name</th>
			<th>Author Name</th>
			<th>Year</th>
			<th>Action</th>
		</tr>
		<%
			int i = 1;
		for (Books k : listOfBooks) {
		%>
		<tr>
			<td><%=i++%></td>
			<td><%=k.getName()%></td>
			<td><%=k.getAuthor()%></td>
			<td><%=k.getYear()%></td>
			<td><a href="search?PurchaseBookid=<%=k.getId()%>">Purchase</a></td>
		</tr>
		<%}%>
	</table>
	<%} %>
</body>
</html>
