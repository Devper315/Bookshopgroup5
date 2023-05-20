package com.group5.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	
	private String name;

	@Override
	public String toString() {
		return name;
	}
	
	
}
