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
ArrayList<Books> listOfBooks = (ArrayList) request.getAttribute("listOfBooks");
if (listOfBooks == null) {
	response.sendRedirect("purchaseBookServlet");
	return;
}
%>
<body>
	<h1>Purchase Books</h1>
	<form action="purchaseBookServlet" method="post">
		<div>
			<label>Select a Book: </label> <select name="book" required>
				<option value="" disabled selected>Select your option</option>
				<%
					for (int count = 0; count < listOfBooks.size(); count++) {
					Books book = (Books) listOfBooks.get(count);
				%>
				<option value="<%=book.getId()%>"><%=book.getName()%>-<%=book.getAuthor() %>-<%=book.getYear() %></option>
				<%}%>
			</select><br>
			<br>
			<button type="submit">Submit</button><br>
			<h1><%=(request.getAttribute("Message") == null) ? "" : request.getAttribute("Message")%></h1>
		</div>
	</form>
</body>
</html>