package com.example.demo.web.rest;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.FavoriteService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.favorite.AddProductFavoriteDTO;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/nest/favorite")
@RestController 
@CrossOrigin("*")
public class FavoriteResource {
	private final FavoriteService favoriteService;

	public FavoriteResource(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}
	
	@GetMapping("/get-favorite")
	@ApiOperation(value = "Lấy danh sách sản phẩm yêu thích")
	public ResponseDTO loadProductById(Long accountId, Pageable pageable) {
		return ResponseDTO.success(this.favoriteService.getFavoriteProducts(accountId, pageable));
	}
	
	@PostMapping("/add")
	@ApiOperation(value = "Thêm sản phẩm vào danh sách yêu thích")
	public ResponseDTO addProductFavorite(@Validated @RequestBody AddProductFavoriteDTO favorite) {
		return ResponseDTO.success(this.favoriteService.addProductFavorite(favorite));
	}
	
	@DeleteMapping("/delete/{accountId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromFavorite(
            @PathVariable Long accountId,
            @PathVariable Long productId) {
        favoriteService.removeProductFromFavorite(accountId, productId);
        return ResponseEntity.noContent().build();
    }
}
