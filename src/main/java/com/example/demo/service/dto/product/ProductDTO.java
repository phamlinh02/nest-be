package com.example.demo.service.dto.product;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
    
    private Long categoryId;

    private String categoryName;
    
    @Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
    
    private Long totalRatings;

    private Double averageRating;
}
