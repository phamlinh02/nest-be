package com.example.demo.service.dto.rate;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDetailDTO {
	private Long id;

    private Long productId;
    	
    private String productName;
    
    private String productImage;

    private Long accountId;
    
    private String accountName;
    
    private String accountImage;
    
    @Temporal(TemporalType.DATE)
	private Date rateDate;

    private Integer star;

    private String comment;
    
    private String image;
}
