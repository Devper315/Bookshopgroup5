package com.group5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group5.repository.AuthorRepository;

@Service
public class AuthorService {
	@Autowired private AuthorRepository authorRepository;
}
