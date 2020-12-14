import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.sql.DriverManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.PrintWriter;


@WebServlet("/login")
public class login extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException
	{
		String user = request.getUserPrincipal().getName();
		if(user!=null){
			HttpSession session = request.getSession(true);
			session.setAttribute("userName", user);
			int userId = UserAction.getUserIdFromName(user);
			List<Integer> allowedOp=UserAction.userOperations(userId);
			List<String> displayList=new ArrayList<String>();
			List<String> fileList=new ArrayList<String>();
			for(int i:allowedOp)
			{
				displayList.add(operationsConstants.operationVsValues.get(i));
				fileList.add(operationsConstants.operationVsValues.get(i)+".jsp");
			}
			request.setAttribute("AllowedOperations",displayList);
			request.setAttribute("fileList",fileList);
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/LogOut.jsp").forward(request, response);
		}	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException , ServletException
	{
		String userName=request.getParameter("username");
		String password=request.getParameter("password");
		try
		{ 
			int userId=UserAction.checkValidCredentials(userName,password);
			if(userId==0){
				PrintWriter out = response.getWriter();
				out.println("<script>");
   				out.println("alert('User or password incorrect');");
				out.println("location='Login.jsp';");
   				out.println("</script>");
			}else{
				HttpSession session = request.getSession(true);
				session.setAttribute("userName", userName);
				session.setAttribute("userId",userId);
				doGet(request, response);
			}
		}
		catch(Exception e)
		{
			response.getWriter().write("error"+e.getMessage());
		}
		
	}
}
