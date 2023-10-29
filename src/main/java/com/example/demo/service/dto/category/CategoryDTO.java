package com.example.demo.service.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
	private Long id;
	
	private String name;
    
    private Boolean isActive;
    
}
