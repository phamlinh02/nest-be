package com.example.demo.service.dto.product;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    private String productName;

    private BigDecimal price;

    private Long quantity;

    private String description;

    private String image;

    private Boolean isActive;
    
    private String categoryId;

    private String categoryName;
}
