import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.sql.DriverManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Objects.Books;
import Objects.Groups;
import javax.servlet.ServletException;
import java.io.PrintWriter;

@WebServlet("/joinGroup")

public class joinGroupServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userName");
		int userId=(int) session.getAttribute("userId");
		if(userName!=null)
		{
			List<Groups> otherGroups=UserAction.showAllExistedGroups(userId);
			request.setAttribute("Groups",otherGroups);
			request.getRequestDispatcher("/joinGroup.jsp").forward(request,response);
		}
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userName");
		int userId=(int) session.getAttribute("userId");
		if(userName!=null) {
			String Message=UserAction.joinGroup(userId,Integer.valueOf(request.getParameter("joinId")),userName);
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('"+Message+"');");
			out.println("location='home.jsp';");
			out.println("</script>");
			return;
		}
}
}