package com.example.demo.service.dto.rate;

import java.util.Date;


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRateDTO {
	private Long id;

    private Long productId;

    private Long accountId;
    
    @Temporal(TemporalType.TIMESTAMP)
	private Date rateDate;

    private Integer star;

    private String comment;
    
    private String image;

}
