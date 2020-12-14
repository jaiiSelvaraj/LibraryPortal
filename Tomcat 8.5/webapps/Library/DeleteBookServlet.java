import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.sql.DriverManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import Objects.Books;
@WebServlet("/deletebook")

public class DeleteBookServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		String adminName = (String) session.getAttribute("adminName");
		if (adminName != null) {
			List<Books> listOfBooks = AdminAction.getBooks();
			request.setAttribute("listOfBooks", listOfBooks);
			request.getRequestDispatcher("/DeleteBook.jsp").forward(request, response);
		} else
			request.getRequestDispatcher("/AdminActions.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException
	{
		HttpSession session = request.getSession();
		String adminName = (String) session.getAttribute("adminName");
		if(adminName!=null)
		{
			String Message=AdminAction.deleteBook(Integer.valueOf(request.getParameter("id")));
			PrintWriter out=response.getWriter();
			out.println("<script>;");
			out.println("alert('"+Message+"');");
			out.println("location='AdminActions.jsp';");
			out.println("</script>;");
		}
}
}