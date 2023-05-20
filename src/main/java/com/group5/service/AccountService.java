package com.group5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.group5.entity.Account;
import com.group5.model.AccountDetails;
import com.group5.repository.AccountRepository;

@Service
public class AccountService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepository;
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Account account = accountRepository.findByUserName(userName);
		if (account == null) {
			throw new UsernameNotFoundException("Tài khoản không tồn tại");
		}
		return new AccountDetails(account);
	}

}
