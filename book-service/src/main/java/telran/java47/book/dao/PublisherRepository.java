package telran.java47.book.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import telran.java47.book.model.Publisher;

public interface PublisherRepository extends PagingAndSortingRepository<Publisher, String> {
	
	@Query("SELECT DISTINCT b.publisher FROM Book b JOIN b.authors a WHERE a.name = :authorName")
    Stream<Publisher> findPublishersByAuthor(@Param("authorName") String authorName);

}

