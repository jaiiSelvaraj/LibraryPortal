import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.sql.DriverManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Objects.Books;
import javax.servlet.ServletException;
import java.io.PrintWriter;

@WebServlet("/createGroup")
public class createGroupServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
			HttpSession session=request.getSession();
			String userName=(String) session.getAttribute("userName");
			int userId=(int) session.getAttribute("userId");
			if(userName!=null)
			{
				String groupName=request.getParameter("groupName");
				String Message=UserAction.createGroup(userId,groupName,userName);
				PrintWriter  out=response.getWriter();
				out.println("<script>");
				out.println("alert('"+Message+"');");
				out.println("location='home.jsp';");
				out.println("</script>");
			return;
				// request.setAttribute("Message",Message);
				// request.getRequestDispatcher("/createGroup.jsp").forward(request,response);
			}
	}
}