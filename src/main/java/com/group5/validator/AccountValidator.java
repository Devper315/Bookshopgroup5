package com.group5.validator;

import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.group5.entity.Account;
import com.group5.form.AccountForm;
import com.group5.repository.AccountRepository;

@Component
public class AccountValidator implements Validator {
	
	private EmailValidator emailValidator = EmailValidator.getInstance();
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == AccountForm.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		AccountForm accountForm = (AccountForm)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName" , null, "Tên đăng nhập không được trống");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", null, "Mật khẩu không được trống");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", null, "Họ tên không được trống");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", null, "Email không được trống");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", null, "Mật khẩu xác nhận không được trống");
		
		if (!this.emailValidator.isValid(accountForm.getEmail())) {
			errors.rejectValue("email", null, "Email không hợp lệ");
		}
		else {
			Account account = accountRepository.findByEmail(accountForm.getEmail());
			if (account != null) {
				errors.rejectValue("email", "", "Email đã được sử dụng");
			}
		}
		
		if (!errors.hasFieldErrors("userName")) {
			Account account = accountRepository.findByUserName(accountForm.getUserName());
			if (account != null) {
				errors.rejectValue("userName", null, "Tên đăng nhập đã được sử dụng");
			}
		}
		
		if(!errors.hasFieldErrors("password")) {
			if (accountForm.getPassword().length() < 8) {
				String message = "Mật khẩu cần có ít nhất 8 ký tự";
				errors.rejectValue("password", null, message);
			}
		}
		
		if (!errors.hasFieldErrors("confirmPassword")) {
			if (!accountForm.getConfirmPassword().equals(accountForm.getPassword())) {
				errors.rejectValue("confirmPassword", null, "Mật khẩu xác nhận không khớp");
			}
		}
	}
}
