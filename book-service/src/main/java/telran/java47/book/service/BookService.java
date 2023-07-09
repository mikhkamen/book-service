package telran.java47.book.service;

import java.util.List;
import java.util.Set;

import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;

public interface BookService {
	
	boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);

	BookDto updateBookTitle(String isbn, String newTitle);
	
	List<BookDto> findBooksByAuthor(String name);

	Set<String> findPublishersByAuthor(String authorName);
	
	List<AuthorDto> findBookAuthors(String isbn);

	List<BookDto> findBooksByPublisher(String publisherName);

	BookDto removeBook(String isbn);
	
	AuthorDto removeAuthor(String authorName);

}
