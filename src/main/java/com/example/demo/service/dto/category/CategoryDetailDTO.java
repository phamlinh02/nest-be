package com.example.demo.service.dto.category;

import java.math.BigDecimal;

import com.example.demo.service.dto.product.ProductDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDetailDTO {
	private Long id;

    private String name;
    
    private String imageCategory;
    
    private Boolean isActive;
}
