package examples.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class TagSearchServlet
 */
@WebServlet("/TagSearch")
public class TagSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tagName = request.getParameter("tagName");
		
		BookTagDAO database = DAOUtilities.getBookTagDAO();
		List<Book> matchedBookList = database.getAllBooksBy(tagName);
		
		if(matchedBookList.size() != 0) {
		    request.getSession().setAttribute("books", matchedBookList);
		}else {
			request.getSession().setAttribute("message", "There is no book matching this tag!");
			request.getSession().setAttribute("messageClass", "alert-warning");
		}
		
		request.getRequestDispatcher("tagSearchResult.jsp").forward(request, response);
	}

}
