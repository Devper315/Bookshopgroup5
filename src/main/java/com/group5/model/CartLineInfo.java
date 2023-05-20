package com.group5.model;

import com.group5.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartLineInfo {
	private Book book;
	private Integer quantity;
	
	public CartLineInfo() {
        this.quantity = 0;
    }
    public Integer getAmount() {
        return this.book.getPrice() * this.quantity;
    }
	
}
