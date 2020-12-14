import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.sql.DriverManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Objects.Books;
import javax.servlet.ServletException;
import java.io.PrintWriter;

@WebServlet("/adminLogin")

public class AdminLoginServlet  extends HttpServlet
{
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException ,ServletException
	{
		String adminUserName=request.getParameter("username");
		String adminPassword=request.getParameter("password");

		if(adminUserName.equals("admin") && adminPassword.equals("admin"))
		{
			HttpSession session=request.getSession(true);
			session.setAttribute("adminName",adminUserName);
			response.sendRedirect("AdminActions.jsp");
		}
		else
		{
			response.sendRedirect("AdminLogin.jsp");
		}
	}
}