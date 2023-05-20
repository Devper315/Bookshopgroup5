package com.group5.entity.compositeKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.group5.entity.Author;
import com.group5.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_author")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAuthor {
	@EmbeddedId
	@Autowired
	private BookAuthorKey bookAuthorKey;
	
	@ManyToOne
	@MapsId("bookId")
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne
	@MapsId("authorId")
	@JoinColumn(name = "author_id")
	private Author author;
	
	public String toString() {
		return this.author.getName();
	}
}
