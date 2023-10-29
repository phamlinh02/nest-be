package com.example.demo.web.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CategoryService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.category.CreateCategoryDTO;
import com.example.demo.service.dto.category.UpdateCategoryDTO;
import com.example.demo.service.dto.product.CreateProductDTO;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/nest/category")
@AllArgsConstructor
public class CategoryResource {
	private final CategoryService categoryService;
	
	@GetMapping("/get-all")
	@ApiOperation(value = "Lấy danh sách danh mục sản phẩm")
	public ResponseDTO getAllCateogory(Pageable pageable) {
		return ResponseDTO.success(this.categoryService.getAllCategory(pageable));
	}
	
	@PostMapping("/save")
	@ApiOperation(value = "Thêm danh mục sản phẩm")
	public ResponseDTO saveCategory(@Validated @RequestBody CreateCategoryDTO category) {
		return ResponseDTO.success(this.categoryService.saveCategory(category));
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "Cập nhật danh mục sản phẩm")
	public ResponseDTO updateCategory(@Validated @RequestBody UpdateCategoryDTO category) {
		return ResponseDTO.success(this.categoryService.updateCategory(category));
	}
}
