package com.group5.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountForm {
	private String userName;
	private String password;
	private String fullName;
	private String email;
	private String confirmPassword;
}
