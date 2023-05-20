package com.group5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group5.entity.Customer;
import com.group5.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
	List<PurchaseOrder> findByCustomer(Customer customer);
}
