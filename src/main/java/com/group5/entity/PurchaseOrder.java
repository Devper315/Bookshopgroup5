package com.group5.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.group5.model.CartInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchaseorder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private Integer amount;
	
	@Column(name = "date_create")
	
	private String dateCreate;
	
	@Column(name = "date_approval")
	private String dateApproval;
	
	@Column(name = "done")
	private Boolean isDone;
	
	@OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
	private Collection<OrderLine> orderLineList;
	
	public PurchaseOrder (CartInfo cartInfo) {
		this.dateCreate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
		this.customer = cartInfo.getCustomer();
		this.amount = cartInfo.getTotalAmount();
	}
}
