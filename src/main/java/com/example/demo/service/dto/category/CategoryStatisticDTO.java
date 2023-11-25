package com.example.demo.service.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryStatisticDTO {
	private String categoryName;
	private long totalProduct;
	private long totalStockQuantity;
}
