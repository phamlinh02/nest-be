package com.example.demo.service.dto.role;

import com.example.demo.service.dto.product.UpdateProductStatusDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleStatusDTO {
	private Long id;
	private boolean isActive;
}
