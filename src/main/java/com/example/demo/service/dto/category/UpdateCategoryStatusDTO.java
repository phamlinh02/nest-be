package com.example.demo.service.dto.category;

import com.example.demo.service.dto.role.UpdateRoleStatusDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryStatusDTO {
	private Long id;
	private boolean isActive;
}
