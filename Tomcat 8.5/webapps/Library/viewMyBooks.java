import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Objects.Books;
import javax.servlet.ServletException;

@WebServlet("/ViewMyBook")

public class viewMyBooks extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		int userId = (int) session.getAttribute("userId");
		if (userName != null) {
			List<Books> myBookInfo = UserAction.viewMyBooks(userId);
			request.setAttribute("myBookInfo", myBookInfo);
			request.getRequestDispatcher("/ViewMyBook.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}

	}
}