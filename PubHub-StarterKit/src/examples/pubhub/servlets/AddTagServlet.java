package examples.pubhub.servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class AddTagServlet
 */
@WebServlet("/AddTag")
public class AddTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("addTag.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tagName = request.getParameter("tagName");
		String isbn13 = request.getParameter("isbn13");
		
		BookDAO bookList = DAOUtilities.getBookDAO();
		BookTagDAO database = DAOUtilities.getBookTagDAO();
		
		Book book = bookList.getBookByISBN(isbn13);
		
		if(database.getAllTagStringsBy(book).contains(tagName)) {
			request.getSession().setAttribute("message", "The tag: " + tagName + " already exists");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("addTag.jsp").forward(request, response);
		}else {
			Tag tag = new Tag();
			tag.setIsbn13(isbn13);
			tag.setName(tagName);
			boolean isSuccess = database.addTag(tag, book);
			
			if(isSuccess){
				request.getSession().setAttribute("message", "Tag successfully added");
				request.getSession().setAttribute("messageClass", "alert-success");

				// We use a redirect here instead of a forward, because we don't
				// want request data to be saved. Otherwise, when
				// a user clicks "refresh", their browser would send the data
				// again!
				// This would be bad data management, and it
				// could result in duplicate rows in a database.
				response.sendRedirect(request.getContextPath() + "/AddTag");
			}else {
				request.getSession().setAttribute("message", "There was a problem adding the tag");
				request.getSession().setAttribute("messageClass", "alert-danger");
				request.getRequestDispatcher("addTag.jsp").forward(request, response);
				
		    }
		}
	}

}
