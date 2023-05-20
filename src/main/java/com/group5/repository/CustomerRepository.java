package com.group5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group5.entity.Account;
import com.group5.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findByAccount(Account account);
}
