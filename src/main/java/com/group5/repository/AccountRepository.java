package com.group5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group5.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	Account findByUserName(String userName);
	Account findByEmail(String email);
}
