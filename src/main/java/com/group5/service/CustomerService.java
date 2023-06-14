package com.group5.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group5.entity.Account;
import com.group5.entity.Customer;
import com.group5.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired private CustomerRepository customerRepository;
	
	public Customer findByAccount(Account account) {
		return customerRepository.findByAccount(account);
	}
	
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Customer update(HttpServletRequest request) {
		Integer id = Integer.valueOf(request.getParameter("id"));
		Customer customer = customerRepository.findById(id).get();
		customer.setFullName(request.getParameter("fullName"));
		customer.setAddress(request.getParameter("address"));
		customer.setPhone(request.getParameter("phone"));
		return customerRepository.save(customer);
	}
	
	
}
