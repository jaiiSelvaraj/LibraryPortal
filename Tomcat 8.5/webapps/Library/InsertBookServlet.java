import java.io.*;
import java.sql.*;
import java.util.*;
import java.sql.DriverManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Objects.Books;
import javax.servlet.ServletException;
import java.io.PrintWriter;

@WebServlet("/insertBooks")

public class InsertBookServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException , ServletException
	{
		HttpSession session=request.getSession();
		String adminName=(String) session.getAttribute("adminName");
		String bookName=request.getParameter("bookname");
		String authorName=request.getParameter("authorname");
		String bookYear=(String)request.getParameter("bookyear");
		String numberOfBooks=(String)request.getParameter("numberofbooks");
		if(adminName!=null)
		{
			try
			{
			String Message=AdminAction.insertBook(bookName,authorName,Integer.valueOf(bookYear),Integer.valueOf(numberOfBooks));
			PrintWriter out=response.getWriter();
			out.println("<script>;");
			out.println("alert('"+Message+"');");
			out.println("location='AdminActions.jsp';");
			out.println("</script>;");
		}
		catch(Exception e)
		{
			response.getWriter().write("error"+e.getMessage());
		}
	}
}
}