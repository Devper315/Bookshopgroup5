package com.group5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group5.entity.Book;
import com.group5.entity.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
	List<OrderLine> findByBook(Book book);
}
