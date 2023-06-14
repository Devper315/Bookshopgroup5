package com.group5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group5.entity.Account;
import com.group5.entity.Admin;
import com.group5.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired AdminRepository adminRepository;
	
	public Admin findByAccount(Account account) {
		return adminRepository.findByAccount(account);
	}
}
