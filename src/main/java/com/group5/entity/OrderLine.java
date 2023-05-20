package com.group5.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.group5.model.CartLineInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orderline")
public class OrderLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	private Integer amount;
	
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "purchaseorder_id")
	@ToString.Exclude
	private PurchaseOrder purchaseOrder;
	
	public OrderLine (CartLineInfo cartLineInfo) {
		this.book = cartLineInfo.getBook();
		this.quantity = cartLineInfo.getQuantity();
		this.amount = cartLineInfo.getAmount();
	}
}
