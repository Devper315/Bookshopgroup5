package com.group5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group5.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
}
