import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Objects.Books;
import javax.servlet.ServletException;

@WebServlet("/search")

public class bookSearch extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		if (request.getParameter("PurchaseBookid") != null) {
			HttpSession session = request.getSession();
			int userId = (int) session.getAttribute("userId");
			String temp=UserAction.purchaseBook(Integer.valueOf(request.getParameter("PurchaseBookid")), userId);
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+temp+"');");
			out.println("location='home.jsp';");
			out.println("</script>");
			return;
		}
		request.getRequestDispatcher("/searchBook.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String searchBookName = request.getParameter("book");
		List<Books> searchList = UserAction.searchBook(searchBookName);
		request.setAttribute("searchBookList", searchList);
		request.getRequestDispatcher("/searchBook.jsp").forward(request, response);

	}
}