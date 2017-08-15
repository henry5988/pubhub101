CREATE TABLE BOOK_TAGS (
   tag_name varchar(255),
   isbn_13 varchar(13),
   constraint CK_isbn_name primary key (tag_name, isbn_13)
);

INSERT into  BOOK_TAGS (tag_name, isbn_13) VALUES ('fiction', '1111111111111');