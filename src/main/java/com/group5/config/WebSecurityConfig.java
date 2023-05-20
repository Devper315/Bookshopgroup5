package com.group5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.group5.service.AccountService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	AccountService accountService;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountService)		// Cung cấp userservice cho spring security
			.passwordEncoder(passwordEncoder());	// cung cấp password encoder
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/", "/signup").permitAll();
		http.authorizeRequests().antMatchers("/buy", "/cart", "/orderList")
		.access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
		
		http.authorizeRequests()
	        .and()
	        .formLogin() // Cho phép người dùng xác thực bằng form login
	        .loginProcessingUrl("/j_spring_security_check")
	        .loginPage("/login")
	        .defaultSuccessUrl("/")
	        .failureUrl("/login?error=true")
	        .usernameParameter("username")
	        .passwordParameter("password")
	        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}
}
