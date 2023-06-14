package com.group5.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group5.entity.Account;
import com.group5.entity.Book;
import com.group5.entity.Customer;
import com.group5.model.CartInfo;
import com.group5.model.CartLineInfo;
import com.group5.repository.AccountRepository;
import com.group5.repository.BookRepository;
import com.group5.repository.CustomerRepository;
import com.group5.repository.TypeRepository;
import com.group5.service.PurchaseOrderService;
import com.group5.utils.Utils;
import com.group5.validator.AccountValidator;

@Controller
public class CustomerController {
@Autowired private BookRepository bookRepository;
	
	@Autowired private TypeRepository typeRepository;
	
	@Autowired private AccountRepository accountRepository;
	
	@Autowired private CustomerRepository customerRepository;
	
	@Autowired private PurchaseOrderService purchaseOrderService;
	
	@Autowired private AccountValidator accountValidator;
	
	@GetMapping("/cart")
	public String cart(Model model, HttpServletRequest request) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		model.addAttribute("cartInfo", cartInfo);
		return "cart";
	}
	
	@GetMapping("/removeBook")
	public String removeBook(HttpServletRequest request,
							@RequestParam(name = "bookID") Integer bookID) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.removeBook(bookID);
		return "redirect:/cart";
	}
	@GetMapping("/buy")
	public String addBook(HttpServletRequest request,
						 @RequestParam(name = "bookID") Integer id) {
		Book book = bookRepository.findById(id).get();
		if (book != null) {
			System.out.println(book);
			CartInfo cartInfo = Utils.getCartInSession(request);
			cartInfo.addBook(book, 1);
		}
		return "redirect:/detail?id=" + id;
	}
	
	@PostMapping("/pay")
	public String pay(Model model, HttpServletRequest request) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		for(int i = 0; i < cartInfo.getCartLines().size(); i++) {
			String sequence = "quantity" + i;
			Integer quantity = Integer.valueOf(request.getParameter(sequence));
			CartLineInfo cartLineInfo = cartInfo.getCartLines().get(i);
			cartLineInfo.setQuantity(quantity);
		}
		String remoteUser = request.getRemoteUser();
		Account account = accountRepository.findByUserName(remoteUser);
		Customer customer = customerRepository.findByAccount(account);
		System.out.println(customer.getFullName());
		cartInfo.setCustomer(customer);
		model.addAttribute(cartInfo);
		return "billAndCheckout";
	}
	
	@PostMapping("/confirm")
	public String confirm(Model model, HttpServletRequest request) {
		purchaseOrderService.save(request);
		
		return "thank";
	}
	
	
}
