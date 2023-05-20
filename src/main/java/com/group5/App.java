package com.group5;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.group5.entity.Book;
import com.group5.entity.Type;
import com.group5.entity.compositeKey.BookType;
import com.group5.entity.compositeKey.BookTypeKey;
import com.group5.repository.BookRepository;
import com.group5.repository.TypeRepository;

@SpringBootApplication
public class App{

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(App.class, args);
		BookRepository bookRepository = context.getBean(BookRepository.class);
	}


}
