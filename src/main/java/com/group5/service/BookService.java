package com.group5.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.group5.entity.Author;
import com.group5.entity.Book;
import com.group5.entity.Publisher;
import com.group5.entity.Type;
import com.group5.entity.compositeKey.BookAuthor;
import com.group5.entity.compositeKey.BookAuthorKey;
import com.group5.entity.compositeKey.BookType;
import com.group5.entity.compositeKey.BookTypeKey;
import com.group5.repository.AuthorRepository;
import com.group5.repository.BookAuthorRepository;
import com.group5.repository.BookRepository;
import com.group5.repository.BookTypeRepository;
import com.group5.repository.PublisherRepository;
import com.group5.repository.TypeRepository;

@Service
public class BookService {
	@Autowired private BookRepository bookRepository;
	
	@Autowired private TypeRepository typeRepository;
	
	@Autowired private AuthorRepository authorRepository;
	
	@Autowired private BookAuthorRepository bookAuthorRepository;
	
	@Autowired private BookTypeRepository bookTypeRepository;
	
	@Autowired private PublisherRepository publisherRepository;
	
	public Book updateTypeList(Book book, String[] typeList) {
		bookTypeRepository.deleteByBook(book.getId());;
		List<BookType> bookTypeList = new ArrayList<>();
		for (String typeName : typeList) {
			Type type = typeRepository.findByName(typeName);
			if (type == null) {
				type = new Type();
				type.setName(typeName);
				type = typeRepository.save(type);
			}
			
			BookTypeKey bookTypeKey = new BookTypeKey(book.getId(), type.getId());
			bookTypeList.add(new BookType(bookTypeKey, book, type));
			
		}
		book.setTypeList(bookTypeList);
		return bookRepository.save(book);
	}
	
	public Book updateBookList(Book book, String[] authorList) {
		bookAuthorRepository.deleteByBook(book.getId());
		List<BookAuthor> bookAuthorList = new ArrayList<>();
		for (String authorName : authorList) {
			Author author = authorRepository.findByName(authorName);
			if (author == null) {
				author = new Author();
				author.setName(authorName);
				author = authorRepository.save(author);
			}
			
			BookAuthorKey bookAuthorKey = new BookAuthorKey(book.getId(), author.getId());
			bookAuthorList.add(new BookAuthor(bookAuthorKey, book, author));
			
		}
		book.setAuthorList(bookAuthorList);
		return bookRepository.save(book);
	}
	
	public Book updatePublisher(Book book, String publisherName) {
		Publisher publisher = publisherRepository.findByName(publisherName);
		if (publisher == null) {
			publisher = new Publisher();
			publisher.setName(publisherName);
		}
		book.setPublisher(publisher);
		return bookRepository.saveAndFlush(book);
	}
}
