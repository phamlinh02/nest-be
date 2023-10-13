package com.example.demo.security.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.domain.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class AccountDetailsImpl implements UserDetails{
	
	 private static final long serialVersionUID = 1L;

	    private Long id;

	    private String username;

	    private String email;
	    
	    private String fullname;
	    
	    private String address;
	    
	    private String phone;
	    
	    private String avatar;

	    @JsonIgnore
	    private String password;
	    
	    private String role;

		public AccountDetailsImpl(Long id, String username, String email, String password, String fullname, String address, String phone, String avatar,
				String role) {
			super();
			this.id = id;
			this.username = username;
			this.email = email;
			this.password = password;
			this.fullname = fullname;
			this.address = address;
			this.phone = phone;
			this.avatar = avatar;
			this.role = role;
		}
		
		public static AccountDetailsImpl build(Account user) {
			String role = user.getAuthorities().stream()
	                .map(authority -> "Role: " + authority.getRole().getRolename())
	                .collect(Collectors.joining(", "));
	        return new AccountDetailsImpl(
	                Long.valueOf(user.getId()),
	                user.getUsername(),
	                user.getEmail(),
	                user.getPassword(),
	        		user.getFullname(),
	        		user.getAddress(),
	        		user.getPhone(),
	        		user.getAvatar(),
	        		role
	        	);
	    }

		public Long getId() {
			return id;
		}

		public String getEmail() {
			return email;
		}
		
		public String getFullname() {
			return fullname;
		}
		
		public String getAddress() {
			return address;
		}
		
		public String getPhone() {
			return phone;
		}
		
		public String getAvatar() {
			return avatar;
		}


		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public String getUsername() {
			return username;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			AccountDetailsImpl user = (AccountDetailsImpl) o;
			return Objects.equals(id, user.id);
		}
}
