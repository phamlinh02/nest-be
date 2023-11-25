package com.example.demo.service.dto.favorite;

import java.math.BigDecimal;

import com.example.demo.config.Constant;
import com.example.demo.service.dto.account.AccountDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDTO {
	private Long id;
	
	private Long accountId;
	
	private Long productId;
	
	private String productName;

    private BigDecimal price;

    private Long quantity;

    private String image;

    private Boolean isActive;
    
    private Long categoryId;

    private String categoryName;
    
    private Long totalRatings;

    private Double averageRating;
}
