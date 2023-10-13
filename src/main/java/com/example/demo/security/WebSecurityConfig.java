package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demo.security.jwt.AuthEntryPointJwt;
import com.example.demo.service.AccountDetailsServiceImpl;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	private final AccountDetailsServiceImpl accountDetailsService;
	private final AuthEntryPointJwt unauthorizedHandler;

	
	
	public WebSecurityConfig(AccountDetailsServiceImpl accountDetailsService, AuthEntryPointJwt unauthorizedHandler) {
		super();
		this.accountDetailsService = accountDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider customAuthenticationProvider() {
	    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	    authenticationProvider.setUserDetailsService(accountDetailsService);
	    authenticationProvider.setPasswordEncoder(passwordEncoder());

	    return authenticationProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.requestMatchers("/**")
		.permitAll().anyRequest()
		.authenticated()
		.and()
		.formLogin().disable()
		.logout()
		.permitAll();
		http.authenticationProvider(customAuthenticationProvider());
		return http.build();
	}
}
