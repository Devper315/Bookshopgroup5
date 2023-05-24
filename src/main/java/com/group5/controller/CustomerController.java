package com.group5.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import com.group5.entity.OrderLine;
import com.group5.entity.PurchaseOrder;
import com.group5.model.CartInfo;
import com.group5.model.CartLineInfo;
import com.group5.repository.AccountRepository;
import com.group5.repository.BookRepository;
import com.group5.repository.CustomerRepository;
import com.group5.repository.PurchaseOrderRepository;
import com.group5.repository.TypeRepository;
import com.group5.utils.Utils;
import com.group5.validator.AccountValidator;

@Controller
public class CustomerController {
@Autowired private BookRepository bookRepository;
	
	@Autowired private TypeRepository typeRepository;
	
	@Autowired private AccountRepository accountRepository;
	
	@Autowired private CustomerRepository customerRepository;
	
	@Autowired private PurchaseOrderRepository purchaseOrderRepository;
	
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
	
	@GetMapping("/pay")
	public String pay(Model model, HttpServletRequest request) {
		String remoteUser = request.getRemoteUser();
		Account account = accountRepository.findByUserName(remoteUser);
		Customer customer = customerRepository.findByAccount(account);
		System.out.println(customer.getFullName());
		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.setCustomer(customer);
		model.addAttribute(cartInfo);
		return "billAndCheckout";
	}
	
	@PostMapping("/confirm")
	public String confirm(Model model, HttpServletRequest request) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		PurchaseOrder purchaseOrder = new PurchaseOrder(cartInfo);
		List<OrderLine> orderLines = new ArrayList<>();
		for (CartLineInfo cartLineInfo : cartInfo.getCartLines()) {
			OrderLine orderLine = new OrderLine(cartLineInfo);
			orderLine.setPurchaseOrder(purchaseOrder);
			orderLines.add(orderLine);
		}
		purchaseOrder.setOrderLineList(orderLines);
		purchaseOrder.setIsDone(false);
		try {
			purchaseOrderRepository.save(purchaseOrder);
			Utils.removeCartInSession(request);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "success";
	}
	
	
}
