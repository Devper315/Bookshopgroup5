package com.group5.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.group5.form.AccountForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "username")
	private String userName;
	
	private String password;
	
	private String email;
	
	private String role;
	
	@Column(name = "decryptedpassword")
	private String decryptedPassword;
	
	public Account (AccountForm accountForm) {
		this.userName = accountForm.getUserName();
		this.decryptedPassword = accountForm.getPassword();
	}
}
