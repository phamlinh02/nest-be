package com.example.demo.service.dto.product;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
	
	private String productName;

	private BigDecimal price;

	private Long quantity;

	private String description;

	private String image;
	
	private Boolean isActive;

	private String categoryName;
}
