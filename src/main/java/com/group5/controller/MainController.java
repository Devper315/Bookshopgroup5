package com.group5.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group5.entity.Account;
import com.group5.entity.Book;
import com.group5.entity.Customer;
import com.group5.entity.OrderLine;
import com.group5.entity.PurchaseOrder;
import com.group5.entity.Type;
import com.group5.form.AccountForm;
import com.group5.model.CartInfo;
import com.group5.model.CartLineInfo;
import com.group5.repository.AccountRepository;
import com.group5.repository.BookRepository;
import com.group5.repository.CustomerRepository;
import com.group5.repository.OrderLineRepository;
import com.group5.repository.PurchaseOrderRepository;
import com.group5.repository.TypeRepository;
import com.group5.utils.Utils;
import com.group5.validator.AccountValidator;

@Controller
public class MainController {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private AccountValidator accountValidator;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired private OrderLineRepository orderLineRepository;

	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null)
			return;
//		System.out.println("Target = " + target);
		if (target.getClass() == AccountForm.class) {
			dataBinder.setValidator(accountValidator);
		}
	}

	@GetMapping("/")
	public String home(Model model, HttpServletRequest request) {
		Collection<Book> books = bookRepository.findAll();
		model.addAttribute("bookList", books);
		Collection<Type> types = typeRepository.findAll();
		model.addAttribute("typeList", types);
		Object cartInfo = Utils.getCartInSession(request);
		model.addAttribute("cartInfo", cartInfo);
		request.getSession().setAttribute("typeList", types);
		return "homepage";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, Model model) {
		Object object = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		model.addAttribute("error", object);
		model.addAttribute("accountForm", new AccountForm());
		return "login";
	}

	@GetMapping("/signup")
	public String getSignup(Model model) {
		model.addAttribute("accountForm", new AccountForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(Model model, @ModelAttribute @Validated AccountForm accountForm, BindingResult result) {
		System.out.println(model.getAttribute("accountForm"));
		if (result.hasErrors()) {
			if (result.getFieldError("userName") != null) {
				model.addAttribute("userNameError", result.getFieldError("userName").getDefaultMessage());
			}
			if (result.getFieldError("password") != null) {
				model.addAttribute("passwordError", result.getFieldError("password").getDefaultMessage());
			}
			if (result.getFieldError("fullName") != null) {
				model.addAttribute("fullNameError", result.getFieldError("fullName").getDefaultMessage());
			}
			if (result.getFieldError("email") != null) {
				model.addAttribute("emailError", result.getFieldError("email").getDefaultMessage());
			}
			if (result.getFieldError("confirmPassword") != null) {
				model.addAttribute("confirmPasswordError", result.getFieldError("confirmPassword").getDefaultMessage());
			}
			return "signup";
		}
		Account account = new Account(accountForm);
		account.setPassword(passwordEncoder.encode(account.getDecryptedPassword()));
		Customer customer = new Customer();
		customer.setFullName(accountForm.getFullName());
		customer.setAccount(account);
		try {
			accountRepository.save(account);
			customerRepository.save(customer);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "signupSuccess";
	}


	@GetMapping("/detail")
	public String detail(Model model, HttpServletRequest request, 
						 @RequestParam(name = "id") Integer id) {
		Book book = bookRepository.findById(id).get();
		model.addAttribute(book);
		Collection<Type> types = typeRepository.findAll();
		model.addAttribute("typeList", types);
		Object cartInfo = Utils.getCartInSession(request);
		model.addAttribute("cartInfo", cartInfo);
		List<OrderLine> orderLines = orderLineRepository.findByBook(book);
		model.addAttribute("waitingNumber", orderLines.size());
		return "detail";
	}

	@GetMapping("/bookImage")
	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("id") Integer id) throws IOException {
		// lấy book trong DB theo id
		Book book = bookRepository.findById(id).get();
		if (book != null && book.getImage() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(book.getImage());
		}
		response.getOutputStream().close();
	}
	@GetMapping("/orderList")
	public String orderList(Model model, HttpServletRequest request) {
		List<PurchaseOrder> orderList;
		String remoteUser = request.getRemoteUser();
		Account account = accountRepository.findByUserName(remoteUser);
		if (account.getRole().equals("ROLE_ADMIN")) {
			orderList = purchaseOrderRepository.findAll();
			model.addAttribute("orderList", orderList);
			
		}
		else {
			Customer customer = customerRepository.findByAccount(account);
			orderList = purchaseOrderRepository.findByCustomer(customer);
			model.addAttribute("orderList", orderList);
			
		}
		return "orderList";
	}
}