<<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="Objects.Groups"%>
<!DOCTYPE html>
<html>
<%
	if(session.getAttribute("userName")==null)
	{
		response.sendRedirect("Login.jsp");
		return;
	}
%>
<%
		ArrayList<Groups> groupsList=(ArrayList) request.getAttribute("Groups");
		if(groupsList==null)
		{
			response.sendRedirect("joinGroup");
			return;
		}
%>
<body>
		<form action="joinGroup" method="post">
			<div>
			<label>Select groupname to join:</label>
			<select name="joinId">
			<option value="" disabled selected>select your option:</option>
				<%
					for(int i=0;i<groupsList.size();i++)
					{
						Groups group=(Groups) groupsList.get(i);
					%>
					<option value="<%=group.getId()%>"><%= group.getName()%></option>
					<%}
				%>
			</select>
			<button type="submit">submit</button>
			<div>
		</form>
	</body>
</html>