package telran.java47.book.service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import telran.java47.book.dao.AuthorRepository;
import telran.java47.book.dao.BookRepository;
import telran.java47.book.dao.PublisherRepository;
import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.model.Author;
import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		// Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		// Authors
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setAuthors(new HashSet<>());
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto updateBookTitle(String isbn, String newTitle) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(newTitle);
		return modelMapper.map(bookRepository.save(book), BookDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BookDto> findBooksByAuthor(String name) {
		Author author = authorRepository.findById(name).orElseThrow(EntityNotFoundException::new);

		return bookRepository.findAllByAuthors(author).map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toList());

	}

	@Override
	@Transactional(readOnly = true)
	public List<BookDto> findBooksByPublisher(String publisherName) {

		Publisher publisher = publisherRepository.findById(publisherName).orElseThrow(EntityNotFoundException::new);
		return bookRepository.findAllByPublisher(publisher).map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuthorDto> findBookAuthors(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);

		return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toList());
	}
//	@Override
//	@Transactional(readOnly = true)
//	public Set<String> findPublishersByAuthor(@PathVariable String authorName) {
//		Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);
//		return bookRepository.findAllByAuthors(author)
//		.map(b -> b.getPublisher())
//		.map(p -> p.toString())
//		.collect(Collectors.toSet());
//	}

	@Override
	@Transactional(readOnly = true)
	public Set<String> findPublishersByAuthor(@PathVariable String authorName) {
		return publisherRepository.findPublishersByAuthor(authorName).map(p -> p.toString())
				.collect(Collectors.toSet());
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);

		bookRepository.findAllByAuthors(author)
			.map(b -> removeBook(b.getIsbn()));
		
		authorRepository.delete(author);
		return modelMapper.map(author, AuthorDto.class);
	}

}
