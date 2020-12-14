import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import Objects.Groups;

@WebServlet("/ViewMyGroup")
 
 public class viewMyGroupServlet  extends HttpServlet{
protected void doGet(HttpServletRequest request,HttpServletResponse response)  throws  IOException,ServletException 
{
	HttpSession session=request.getSession();
	String userName=(String) session.getAttribute("userName");
	int userId=(int) session.getAttribute("userId");
	if(userName!=null)
	{
		List<Groups> myGroupList=UserAction.viewMyGroupInfo(userId);
		request.setAttribute("myGroupList",myGroupList);
		request.getRequestDispatcher("viewMygroupInfo.jsp").forward(request,response);
	}
}
}