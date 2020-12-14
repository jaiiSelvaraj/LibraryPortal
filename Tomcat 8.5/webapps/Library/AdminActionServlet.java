import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.sql.DriverManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Objects.Books;
import javax.servlet.ServletException;
import java.io.PrintWriter;

@WebServlet("/adminOperations")

public class AdminActionServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		HttpSession session=request.getSession();
		String adminName=(String) session.getAttribute("adminName");
		if(adminName!=null)
		{
			List<String> adminOperations=AdminAction.adminOp();
			List<String> adminOperationList=new ArrayList<String>();
			for(int k=0;k<adminOperations.size();k++)
			{
				adminOperationList.add(adminOperations.get(k)+".jsp");
			}
			request.setAttribute("adminActionsList",adminOperationList);
			request.setAttribute("adminOperationList",adminOperations);
			request.getRequestDispatcher("/AdminActions.jsp").forward(request,response);
		}
		else
		{
			request.getRequestDispatcher("/AdminLogin.jsp").forward(request,response);
		}
	}
}