package examples.pubhub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

public class BookTagDAOImpl implements BookTagDAO {
	
	Connection connection = null;	//connection to the database
	PreparedStatement stmt = null;

	@Override
	public List<String> getAllTagStringsBy(Book book) {
		List<String> tags = new ArrayList<>();
		String isbn13 = book.getIsbn13();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT tag_name FROM BOOK_TAGS WHERE isbn_13 LIKE ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, "%" + isbn13 + "%");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String tag = rs.getString("tag_name");
				tags.add(tag);
			}
			
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				closeResources();
		}

		return tags;
	}

	@Override
	public List<Tag> getAllTagsBy(Book book){
		List<Tag> tags = new ArrayList<>();
		String isbn13 = book.getIsbn13();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT tag_name FROM BOOK_TAGS WHERE isbn_13 LIKE ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, "%" + isbn13 + "%");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Tag tag = new Tag();
				tag.setName(rs.getString("tag_name"));
				tag.setIsbn13(rs.getString("isbn_13"));
				tags.add(tag);
			}
			
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				closeResources();
		}

		return tags;
	}
	
	@Override
	public List<Book> getAllBooksBy(String tagName) {
		List<Book> books = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM (SELECT * FROM BOOKS AS b INNER JOIN BOOK_TAGS AS t ON b.isbn_13 = t.isbn_13) WHERE tag_name=?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, tagName);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Book book = new Book();

				// Each variable in our Book object maps to a column in a row from our results.
				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));
				
				books.add(book);
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return books;
	}

	@Override
	public boolean addTag(Tag tag, Book book) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO BOOK_TAGS VALUES (?, ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, tag.getName());
			stmt.setString(2, book.getIsbn13());
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			closeResources();
		}
	}

	@Override
	public boolean removeTag(Tag tag, Book book) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE * FROM BOOK_TAGS WHERE tag_name LIKE ? AND isbn_13 LIKE ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, "%" + tag.getName() + "%");
			stmt.setString(2, "%" + book.getIsbn13() + "%");
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			closeResources();
		}
	}
	
	/*------------------------------------------------------------------------------------------------*/

	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

}
