package com.group5.entity.compositeKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.group5.entity.Book;
import com.group5.entity.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookType {
	@EmbeddedId
	@Autowired
	private BookTypeKey bookTypeKey;
	
	@ManyToOne
	@MapsId("book")
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne
	@MapsId("type")
	@JoinColumn(name = "type_id")
	private Type type;
	
	public String toString() {
		return this.type.getName();
	}
}
