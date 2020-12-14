<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
</head>
<%
	session.invalidate();
%>
<body>
		<div>
			<h1>Logged Out</h1>
		</div>
		<p >
			Please <a href="home.jsp">Login</a> 
		</p>
	</div>
</body>
</html>
