package com.example.demo.service.dto.role;

import com.example.demo.config.Constant;
import com.example.demo.service.dto.category.CategoryDTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

	private Long id;

	@Enumerated(EnumType.STRING)
	private Constant.ROLE_USER roleName;

	private Boolean isActive;

}
