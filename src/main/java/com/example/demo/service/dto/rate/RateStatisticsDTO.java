package com.example.demo.service.dto.rate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateStatisticsDTO {
	 private int totalRates;
	    private double averageStar;
	    private int fiveStarCount;
	    private int fourStarCount;
	    private int threeStarCount;
	    private double twoStarCount;
	    private double oneStarCount;
}
