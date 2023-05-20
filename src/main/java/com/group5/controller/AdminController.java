package com.group5.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group5.entity.Book;
import com.group5.entity.PurchaseOrder;
import com.group5.repository.AccountRepository;
import com.group5.repository.AuthorRepository;
import com.group5.repository.BookAuthorRepository;
import com.group5.repository.BookRepository;
import com.group5.repository.BookTypeRepository;
import com.group5.repository.CustomerRepository;
import com.group5.repository.PublisherRepository;
import com.group5.repository.PurchaseOrderRepository;
import com.group5.repository.TypeRepository;
import com.group5.service.BookService;

@Controller
public class AdminController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private PublisherRepository publisherRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private BookAuthorRepository bookAuthorRepository;

	@Autowired
	private BookTypeRepository bookTypeRepository;
	
	@Autowired BookService bookService;

	@GetMapping("/edit-book")
	public String editBook(Model model, 
							 @RequestParam(name = "bookID", required = false) Integer id,
							 @RequestParam(name = "action", required = false) String action,
							 @RequestParam(name = "waitingNumber", required = false) Integer waitingNumber){
		if(action != null && action.equals("delete")){
			if(waitingNumber != 0) {
				return "redirect:/detail?id=" + id;
			}
			bookRepository.deleteById(id);
			return "redirect:/";
		}
		Book book;
		if(id != null) book = bookRepository.findById(id).get();
		else book = new Book();
		model.addAttribute(book);
		return "book";
	}
	
	
	@PostMapping("/edit-book")
	public String saveBook(Model model, HttpServletRequest request,
						   @ModelAttribute Book book) throws IOException {
		String[] authorList = splitInfo(request.getParameter("authorInfo"));
		String[] typeList = splitInfo(request.getParameter("typeInfo"));
		String publisherName = request.getParameter("publisherName");
		
		byte [] image = book.getFileData().getBytes();
		if (book.getIsNewBook() != null && book.getIsNewBook()) {
			book.setImage(image);
			book = bookRepository.save(book);
			try {
				book = bookService.updateBookList(book, authorList);
				book = bookService.updateTypeList(book, typeList);
				book = bookService.updatePublisher(book, publisherName);
			} catch (Exception e) {
				System.out.println(e);
			}
			return "redirect:/detail?id=" + book.getId();
		}
		else {
			Book updateBook = bookRepository.findById(book.getId()).get();
			updateBook.setName(book.getName());
			updateBook.setPublisher(book.getPublisher());
			updateBook.setRemaining(book.getRemaining());
			updateBook.setPrice(book.getPrice());
			updateBook.setPublishingDate(book.getPublishingDate());
			updateBook.setNumberOfPage(book.getNumberOfPage());
			updateBook.setDescription(book.getDescription());
			if (image.length != 0) updateBook.setImage(image);
			try {
				updateBook = bookService.updateBookList(updateBook, authorList);
				updateBook = bookService.updateTypeList(updateBook, typeList);
				updateBook = bookService.updatePublisher(updateBook, publisherName);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return "redirect:/detail?id=" + book.getId();
	}
	
	@GetMapping("/add-book")
	public String addBook(Model model, HttpServletRequest request) {
		Book book = new Book();
		book.setIsNewBook(true);
		model.addAttribute("book", book);
		return "book";
	}
	
	@GetMapping("/approval")
	public String approval(@RequestParam String action,
						   @RequestParam Integer orderID) {
		PurchaseOrder order = purchaseOrderRepository.findById(orderID).get();
		if(action.equals("approval")) {
			order.setDateApproval(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));
			order.setIsDone(true);
		}
		if(action.equals("cancel")) {
			order.setDateApproval(null);
			order.setIsDone(false);
		}
		purchaseOrderRepository.save(order);
		return "redirect:/orderList";
	}
	
	public String[] splitInfo(String info) {
		String[] w = info.split(",");
		for (int i = 0; i < w.length; i++) {
			w[i] = w[i].strip();
		}
		return w;
	}
}
