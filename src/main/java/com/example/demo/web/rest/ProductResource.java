package com.example.demo.web.rest;

import lombok.AllArgsConstructor;


import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ProductService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.product.CreateProductDTO;
import com.example.demo.service.dto.product.UpdateProductDTO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/nest/product")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductResource {
	private final ProductService productService;

	@GetMapping("/get-all")
	@ApiOperation(value = "Lấy danh sách sản phẩm")
	public ResponseDTO getAllUser(Pageable pageable) {
		return ResponseDTO.success(this.productService.getAllProduct(pageable));
	}

	@GetMapping("/get-product")
	@ApiOperation(value = "Lấy thông tin sản phẩm")
	public ResponseDTO loadUserByUsername(Long id) {
		return ResponseDTO.success(this.productService.loadProductById(id));
	}

	@PostMapping("/save")
	@ApiOperation(value = "Thêm sản phẩm")
	public ResponseDTO saveUser(@Validated @RequestBody CreateProductDTO product) {
		return ResponseDTO.success(this.productService.saveProduct(product));
	}

	@PostMapping("/update")
	@ApiOperation(value = "Cập nhật sản phẩm")
	public ResponseDTO saveUser(@Validated @RequestBody UpdateProductDTO product) {
		return ResponseDTO.success(this.productService.updateProduct(product));
	}

	@GetMapping("/search-by-name")
	@ApiOperation(value = "Tìm kiếm sản phẩm theo tên")
	public ResponseDTO searchProductsByName(@RequestParam String productName, Pageable pageable) {
		return ResponseDTO.success(this.productService.searchProductsByName(productName, pageable));
	}
	
	@GetMapping("/show-by-category")
	@ApiOperation(value = "Hiển thị sản phẩm theo category")
	public ResponseDTO showProductsByCategory(@RequestParam Long categoryId, Pageable pageable) {
		return ResponseDTO.success(this.productService.showProductsByCategory(categoryId, pageable));
	}
}
