package com.example.demo.service.dto.account;

import com.example.demo.config.Constant;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO{

	private Long id;
	
	private String username;

	private String password;

	private String email;

	private String fullName;

	private String address;

	private String phone;

	private String avatar;
	
	@Enumerated(EnumType.STRING)
	private Constant.ROLE_USER roleName;
	
	private Boolean isActive;
}
