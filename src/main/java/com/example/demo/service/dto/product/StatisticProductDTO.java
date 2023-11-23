package com.example.demo.service.dto.product;

import java.util.List;

import com.example.demo.service.dto.category.CategoryStatisticDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticProductDTO {
	private List<CategoryStatisticDTO> categoryStatistics;
}
