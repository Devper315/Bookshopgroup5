package com.group5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group5.entity.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
	Publisher findByName(String name);
}
