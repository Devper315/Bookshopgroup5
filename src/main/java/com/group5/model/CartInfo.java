package com.group5.model;

import java.util.ArrayList;
import java.util.List;

import com.group5.entity.Book;
import com.group5.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartInfo {
	private Customer customer;

	private List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

	private CartLineInfo findLineByBookId(Integer id) {
		for (CartLineInfo line : this.cartLines) {
			if (line.getBook().getId() == id) {
				return line;
			}
		}
		return null;
	}

	public void addBook(Book book, Integer quantity) {
		CartLineInfo line = this.findLineByBookId(book.getId());
		if (line == null) {
			line = new CartLineInfo();
			line.setBook(book);
			line.setQuantity(0);
			this.cartLines.add(line);
		}
		int newQuantity = line.getQuantity() + quantity;
		if (quantity <= 0) {
			this.cartLines.remove(line);
		} else {
			line.setQuantity(newQuantity);
		}
	}

	public void updateBook(Integer id, Integer quantity) {
		CartLineInfo line = this.findLineByBookId(id);
		if (line != null) {
			if (quantity <= 0)
				this.cartLines.remove(line);
			else
				line.setQuantity(quantity);
		}
	}

	public void removeBook(Integer id) {
		CartLineInfo line = this.findLineByBookId(id);
		this.cartLines.remove(line);
	}

	public boolean isEmpty() {
		return this.cartLines.isEmpty();
	}

	public Integer getAmount() {
		Integer amount = 0;
		for (CartLineInfo x : this.cartLines) {
			amount += x.getAmount();
		}
		return amount;
	}
	
	public Integer getDeliveryCharges() {
		Long deliveryCharges = Math.round(getAmount() * 10 / 100.0);
		return Integer.valueOf(String.valueOf(deliveryCharges));
	}
	
	public Integer getTotalAmount() {
		return getAmount() + getDeliveryCharges();
	}
}
