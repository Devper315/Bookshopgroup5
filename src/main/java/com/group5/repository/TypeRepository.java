package com.group5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group5.entity.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
	Type findByName (String name);
}
