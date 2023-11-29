package com.example.demo.service.dto.account;

import com.example.demo.service.dto.product.UpdateProductStatusDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusAccountDTO {
	private Long id;
	private boolean isActive;
}
