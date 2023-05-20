package com.group5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.group5.entity.Author;
import com.group5.entity.compositeKey.BookAuthor;
import com.group5.entity.compositeKey.BookAuthorKey;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorKey> {
	List<BookAuthor> findByAuthor(Author author);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM book_author WHERE book_id = ?1", nativeQuery = true)
	void deleteByBook(Integer id);
	
}
