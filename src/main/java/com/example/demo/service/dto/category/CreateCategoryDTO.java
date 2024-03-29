package com.example.demo.service.dto.category;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDTO {
	private String name;
	
	private MultipartFile imageCategory;

	private Boolean isActive;
}
