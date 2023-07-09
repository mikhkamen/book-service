package telran.java47.book.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController{
	final BookService bookService;

	@PostMapping("/book")
	public boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}

	@GetMapping("/book/{isbn}")
	public BookDto findBookByIsbn(@PathVariable String isbn) {
		return bookService.findBookByIsbn(isbn);
	}
	
	@DeleteMapping("/book/{isbn}")
	public BookDto removeBook(@PathVariable String isbn) {
		return bookService.removeBook(isbn);
	}
	
	@PutMapping("/book/{isbn}/title/{newTitle}")
	public BookDto updateBookTitle(@PathVariable String isbn, @PathVariable String newTitle) {
		return bookService.updateBookTitle(isbn, newTitle);
	}
	
	@GetMapping("/books/author/{author}")
	public List<BookDto> findBooksByAuthor(@PathVariable String author){
		return bookService.findBooksByAuthor(author);
	}
	
	@GetMapping("/books/publisher/{publisherName}")
	public List<BookDto> findBooksByPublisher(@PathVariable String publisherName){
		return bookService.findBooksByPublisher(publisherName);
	}
	
	@GetMapping("/authors/book/{isbn}")
	public List<AuthorDto> findBookAuthors(@PathVariable String isbn){
		return bookService.findBookAuthors(isbn);
		
	}
	
	@GetMapping("/publishers/author/{authorName}")
	public Set<String>findPublishersByAuthor(@PathVariable String authorName){
		return bookService.findPublishersByAuthor(authorName);
	}
	
	@DeleteMapping("/author/{authorName}")
	public AuthorDto removeAuthor(@PathVariable String authorName) {
		return bookService.removeAuthor(authorName);
	}

}
