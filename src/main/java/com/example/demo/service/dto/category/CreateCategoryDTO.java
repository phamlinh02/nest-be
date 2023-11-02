package com.example.demo.service.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDTO {
	private String name;
	
	private String imageCategory;

	private Boolean isActive;
}
