package examples.pubhub.model;

public class Tag {
	
	//fields
	private String name;
	private String isbn13;
	
	//constructors
	public Tag(String name, String isbn13) {
		this.name = name;
	    this.isbn13 = isbn13;
	}
	
	public Tag() {
		this.name = null;
		this.isbn13 = null;
	}

	//getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}
	
	

}
