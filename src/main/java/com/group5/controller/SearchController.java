package com.group5.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group5.entity.Author;
import com.group5.entity.Book;
import com.group5.entity.Publisher;
import com.group5.entity.Type;
import com.group5.entity.compositeKey.BookAuthor;
import com.group5.entity.compositeKey.BookType;
import com.group5.repository.AccountRepository;
import com.group5.repository.AuthorRepository;
import com.group5.repository.BookAuthorRepository;
import com.group5.repository.BookRepository;
import com.group5.repository.BookTypeRepository;
import com.group5.repository.CustomerRepository;
import com.group5.repository.PublisherRepository;
import com.group5.repository.PurchaseOrderRepository;
import com.group5.repository.TypeRepository;
import com.group5.utils.Utils;
import com.group5.validator.AccountValidator;

@Controller
public class SearchController {
	@Autowired private BookRepository bookRepository;
	
	@Autowired private AuthorRepository authorRepository;
	
	@Autowired private TypeRepository typeRepository;
	
	@Autowired private PublisherRepository publisherRepository;
	
	@Autowired private AccountRepository accountRepository;
	
	@Autowired private CustomerRepository customerRepository;
	
	@Autowired private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired private BookAuthorRepository bookAuthorRepository;
	
	@Autowired private BookTypeRepository bookTypeRepository;
	
	@GetMapping("/search")
	public String search(Model model, HttpServletRequest request,
						@RequestParam(name = "keyword") String keyword,
						@RequestParam(name = "field") String field) {
		Collection<Type> types = typeRepository.findAll();
		model.addAttribute("typeList", types);
		Object cartInfo = Utils.getCartInSession(request);
		model.addAttribute("cartInfo", cartInfo);
		if(!keyword.equals("type")) model.addAttribute("keyword", keyword);
		model.addAttribute("field", field);
		Collection<Book> books = findByKeyword(keyword, field);
		System.out.println(books.size());
		model.addAttribute("bookList", books);
		return "homepage";
	}
	
	public Collection<Book> findByKeyword(String keyword, String field){
		Collection<Book> books = new ArrayList<>();
		if (field.equals("name")) {
			books = bookRepository.findByName(keyword);
		}
		if (field.equals("author")) {
			Author author = authorRepository.findByName(keyword);
			Collection<BookAuthor> bookAuthors = bookAuthorRepository.findByAuthor(author);
			for (BookAuthor bookAuthor : bookAuthors) books.add(bookAuthor.getBook());
		}
		if (field.equals("publisher")) {
			Publisher publisher = publisherRepository.findByName(keyword);
			books = bookRepository.findByPublisher(publisher);
		}
		if (field.equals("type")) {
			Type type = typeRepository.findByName(keyword);
			Collection<BookType> bookTypes = bookTypeRepository.findByType(type);
			for (BookType bookType : bookTypes) {
				books.add(bookType.getBook());
			}
		}
		return books;	
	}
}
