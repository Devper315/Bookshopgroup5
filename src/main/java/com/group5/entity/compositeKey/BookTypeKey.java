package com.group5.entity.compositeKey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BookTypeKey implements Serializable {
	@Column(name = "book_id")
	private Integer bookId;

	@Column(name = "type_id")
	private Integer typeId;
}
