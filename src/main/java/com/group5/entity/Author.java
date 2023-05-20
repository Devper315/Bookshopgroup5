package com.group5.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.group5.entity.compositeKey.BookAuthor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ToString.Exclude
	private Integer id;
	
	private String name;
	
	@OneToMany(mappedBy = "author")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<BookAuthor> bookList;
}
