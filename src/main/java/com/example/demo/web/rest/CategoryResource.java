package com.example.demo.web.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CategoryService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.category.CreateCategoryDTO;
import com.example.demo.service.dto.category.UpdateCategoryDTO;
import com.example.demo.service.dto.category.UpdateCategoryStatusDTO;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/nest/category")
@AllArgsConstructor
@CrossOrigin("*")
public class CategoryResource {
	private final CategoryService categoryService;
	
	@GetMapping("/get-all-active")
	@ApiOperation(value = "Lấy danh sách danh mục sản phẩm")
	public ResponseDTO getAllCateogoryIsActive(Pageable pageable) {
		return ResponseDTO.success(this.categoryService.getAllCategoryIsActive(pageable));
	}

	@GetMapping("/statistic-category")
	@PreAuthorize("hasRole('ADMIN') or hasRole('DIRECTOR')")
	@ApiOperation(value = "Thống kê số lượng lọai sản phẩm")
	public ResponseDTO statisticCategory() {
		return ResponseDTO.success(this.categoryService.calculateTotalCatory());
	}
	
	@GetMapping("/get-all")
	@ApiOperation(value = "Lấy danh sách danh mục sản phẩm")
	public ResponseDTO getAllCateogory(Pageable pageable) {
		return ResponseDTO.success(this.categoryService.getAllCategory(pageable));
	}
	
	@PostMapping("/save")
	@ApiOperation(value = "Thêm danh mục sản phẩm")
	public ResponseDTO saveCategory(@Validated @ModelAttribute CreateCategoryDTO category,
    	    @RequestParam(value="categoryFile", required = false) MultipartFile categoryFile) {
		return ResponseDTO.success(this.categoryService.saveCategory(category,categoryFile));
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "Cập nhật danh mục sản phẩm")
	public ResponseDTO updateCategory(@Validated @ModelAttribute UpdateCategoryDTO category,
    	    @RequestParam(value="categoryFile", required = false) MultipartFile categoryFile) {
		return ResponseDTO.success(this.categoryService.updateCategory(category,categoryFile));
	}
	
	@GetMapping("/get-category")
	@ApiOperation(value = "Lấy thông tin danh mục sản phẩm")
	public ResponseDTO loadCategoryById(Long id) {
		return ResponseDTO.success(this.categoryService.loadCategoryById(id));
	}
	
	@PostMapping("/update-status")
	@ApiOperation(value = "Cập nhật trạng thái danh mục sản phẩm")
	public ResponseDTO updateCategoryStatus(@RequestBody UpdateCategoryStatusDTO updateStatusDTO) {
	    return ResponseDTO.success(this.categoryService.updateCategoryStatus(updateStatusDTO));
	}
}
