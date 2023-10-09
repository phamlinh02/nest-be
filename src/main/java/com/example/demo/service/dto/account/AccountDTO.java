package com.example.demo.service.dto.account;

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

	private String fullname;

	private String address;

	private String phone;

	private String avatar;
}
