<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="Objects.Groups"%>
<!DOCTYPE html>
<html>
<%
	if (session.getAttribute("userName") == null) {
	response.sendRedirect("Login.jsp");
	return;
	}
%>
<%
	ArrayList<Groups> listMyGroups=(ArrayList) request.getAttribute("myGroupList");
	if(listMyGroups==null)
	{
		response.sendRedirect("ViewMyGroup");
		return;
	}
%>
<body>
		<h1>My Groups:</h1>
		<table>
			<tr>
				<th>GroupId</th>
				<th>GroupName</th>
			</tr>
		<tr>
		<%
			for(int count=0;count<listMyGroups.size();count++)
			{
				Groups group=(Groups) listMyGroups.get(count);
			%>
				<td><%=group.getId()%></td>
				<td><%= group.getName()%></td></tr>
			<%}
			%>
		</tr>
	</table>
</body>
</html>