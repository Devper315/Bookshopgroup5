package com.group5.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.group5.entity.compositeKey.BookAuthor;
import com.group5.entity.compositeKey.BookType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@ToString.Exclude
	private Integer remaining;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "publisher_id")
	private Publisher publisher;
	
	private Integer price;
	
	private String publishingDate;
	
	private String description;
	
	private Integer numberOfPage;
	
	@Transient
	private Boolean isNewBook;
	
	@Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
	@ToString.Exclude
    private byte[] image;
	
	@Transient
	@ToString.Exclude
	private MultipartFile fileData;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<BookAuthor> authorList;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<BookType> typeList;
	
	public String printAuthors() {
		String s = "";
		if (authorList == null || authorList.size() == 0) return "";
		if (authorList.size() > 0) {
			for (BookAuthor bookAuthor : authorList) {
				s += bookAuthor.getAuthor().getName() + ", ";
			}
		}
		return s.substring(0, s.length() - 2);
	}
	
	public String printTypes() {
		String s = "";
		if (typeList == null || typeList.size() == 0) return "";
		if (typeList != null) {
			for (BookType bookType : typeList) {
				s += bookType.getType().getName() + ", ";
			}
		}
		return s.substring(0, s.length() - 2);
	}
	
}
