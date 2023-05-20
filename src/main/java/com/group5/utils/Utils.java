package com.group5.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.group5.entity.Account;
import com.group5.entity.Customer;
import com.group5.model.CartInfo;
import com.group5.repository.AccountRepository;
import com.group5.repository.CustomerRepository;

public class Utils {

	public static CartInfo getCartInSession(HttpServletRequest request) {
		CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");
		if (cartInfo == null) {
			cartInfo = new CartInfo();
			request.getSession().setAttribute("myCart", cartInfo);
		}
		return cartInfo;
	}

	public static void removeCartInSession(HttpServletRequest request) {
		request.getSession().removeAttribute("myCart");
	}

	public static void storedLastOrderedCartInSession(HttpServletRequest request, CartInfo cartInfo) {
		request.setAttribute("lastOrderedCart", cartInfo);
	}

	public static CartInfo getLastOrderedCartInSession(HttpServletRequest request) {
		return (CartInfo) request.getSession().getAttribute("lastOrderedCart");
	}

}
