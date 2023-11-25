package com.example.demo.service.dto.favorite;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductFavoriteDTO {
	private Long productId;
	private Long accountId;
}
