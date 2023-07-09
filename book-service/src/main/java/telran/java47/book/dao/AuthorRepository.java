package telran.java47.book.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import telran.java47.book.model.Author;

public interface AuthorRepository extends PagingAndSortingRepository<Author, String> {

	public void deleteById(String authorName) ;

	@Query("DELETE FROM Book b WHERE b.id IN (SELECT b.id FROM Book b JOIN b.authors a WHERE a.name = :authorName)")
	void deleteBooksByAuthorName(@Param("authorName") String authorName);

//	    @Modifying
//	    @Query("DELETE FROM Author a WHERE a = :author")
//	    void removeAuthor(@Param("author") Author author);
}
