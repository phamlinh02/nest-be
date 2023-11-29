package com.example.demo.service.dto.product;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO {
	
	private Long id;
	
	private String productName;

	private BigDecimal price;

	private Long quantity;

	private String description;

	private MultipartFile image;
	
	private Boolean isActive;
	
	private String endDate;

	private String categoryName;
}
