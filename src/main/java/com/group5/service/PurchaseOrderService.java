package com.group5.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group5.entity.Account;
import com.group5.entity.Customer;
import com.group5.entity.OrderLine;
import com.group5.entity.PurchaseOrder;
import com.group5.model.CartInfo;
import com.group5.model.CartLineInfo;
import com.group5.repository.PurchaseOrderRepository;
import com.group5.utils.Utils;

@Service
public class PurchaseOrderService {
	@Autowired PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired AccountService accountService;
	
	@Autowired CustomerService customerService;
	
	public List<PurchaseOrder> findAll(){
		return purchaseOrderRepository.findAll();
	}
	
	public List<PurchaseOrder> findByRole(HttpServletRequest request){
		List<PurchaseOrder> orderList = null;
		String remoteUser = request.getRemoteUser();
		Account account = accountService.findByUserName(remoteUser);
		if (account.getRole().equals("ROLE_ADMIN")) {
			orderList = purchaseOrderRepository.findAll();
			
		}
		else {
			Customer customer = customerService.findByAccount(account);
			orderList = purchaseOrderRepository.findByCustomer(customer);
		}
		return orderList;
		
	}
	
	public void save(HttpServletRequest request) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		PurchaseOrder purchaseOrder = new PurchaseOrder(cartInfo);
		purchaseOrder.setAddress(request.getParameter("address"));
		purchaseOrder.setNote(request.getParameter("note"));
		List<OrderLine> orderLines = new ArrayList<>();
		for (CartLineInfo cartLineInfo : cartInfo.getCartLines()) {
			OrderLine orderLine = new OrderLine(cartLineInfo);
			orderLine.setPurchaseOrder(purchaseOrder);
			orderLines.add(orderLine);
		}
		purchaseOrder.setOrderLineList(orderLines);
		purchaseOrder.setIsDone(false);
		purchaseOrderRepository.save(purchaseOrder);
		Utils.removeCartInSession(request);
	}
}
