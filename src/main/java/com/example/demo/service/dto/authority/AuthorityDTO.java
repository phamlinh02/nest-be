package com.example.demo.service.dto.authority;

import com.example.demo.config.Constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityDTO {
	private Long id;

	private Long accountId;
	
	private String username;
	
	private String fullName;
	
	private String avatar;

	private Long roleId;
	
	private Constant.ROLE_USER roleName;
}
