package com.group5.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.group5.entity.Type;
import com.group5.entity.compositeKey.BookType;
import com.group5.entity.compositeKey.BookTypeKey;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, BookTypeKey>{
	Collection<BookType> findByType(Type type);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM book_type WHERE book_id = ?1", nativeQuery = true)
	void deleteByBook(Integer id);
}
