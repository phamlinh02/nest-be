package com.example.demo.service.dto.rate;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRateDTO {

	private Long productId;

    private Long accountId;

    private Integer star;
    
    @Temporal(TemporalType.TIMESTAMP)
	private Date rateDate;

    private String comment;
    
    private MultipartFile image;
}
