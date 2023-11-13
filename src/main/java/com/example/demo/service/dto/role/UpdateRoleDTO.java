package com.example.demo.service.dto.role;

import com.example.demo.config.Constant;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleDTO {
	private Long id;

	@Enumerated(EnumType.STRING)
	private Constant.ROLE_USER roleName;
	
	private Boolean isActive;
}
