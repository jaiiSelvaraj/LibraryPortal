<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="Objects.Books" %>
<!DOCTYPE html>
<html>
<%
	if(session.getAttribute("adminName")==null)
	{
		response.sendRedirect("AdminLogin.jsp");
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
	<form action="deletebook" method="post">
	<label>Select book to remove:</label>
	<select name="id">
	<option value="" disabled selected>Choose a book:</option>
	<%
		for(int i=0;i<listOfBooks.size();i++){
			Books book=(Books) booksList.get(i);
			%>
			<option value="<%=book.getId()%>"><%=book.getName()%></option>
		<%}%>
	%>
	</select>
	<button>Submit</button>
	</form>
	</body>
</html>