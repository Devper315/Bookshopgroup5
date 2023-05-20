package com.group5.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group5.entity.Book;
import com.group5.entity.Publisher;
import com.group5.entity.compositeKey.BookType;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	@Query(value = "SELECT * FROM Book WHERE name like %?1%", nativeQuery = true)
	Collection<Book> findByName(String name);
	
	Collection<Book> findByPublisher(Publisher publisher);
	
	Collection<Book> findByTypeListIn(Set<BookType> typeList);
	
	
	
}
