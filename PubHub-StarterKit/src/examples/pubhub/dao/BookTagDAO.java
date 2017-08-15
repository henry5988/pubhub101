package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;

public interface BookTagDAO {

	public List<String> getAllTagStringsBy(Book book);
	public List<Tag> getAllTagsBy(Book book);
	public List<Book> getAllBooksBy(String tagName);
	public boolean addTag(Tag tag, Book book);
	public boolean removeTag(Tag tag, Book book);
}
