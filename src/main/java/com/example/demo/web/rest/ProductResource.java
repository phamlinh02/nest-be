package com.example.demo.web.rest;

import lombok.AllArgsConstructor;


import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.ProductService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.product.CreateProductDTO;
import com.example.demo.service.dto.product.UpdateProductDTO;
import com.example.demo.service.dto.product.UpdateProductStatusDTO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/nest/product")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductResource {
	private final ProductService productService;

	@GetMapping("/get-all-active")
	@ApiOperation(value = "Lấy danh sách sản phẩm")
	public ResponseDTO getAllProductIsActive(Pageable pageable) {
		return ResponseDTO.success(this.productService.getAllProductIsActive(pageable));
	}
	@GetMapping("/get-all")
	@ApiOperation(value = "Lấy danh sách sản phẩm")
	public ResponseDTO getAllProduct(Pageable pageable) {
		return ResponseDTO.success(this.productService.getAllProduct(pageable));
	}

	@GetMapping("/get-product")
	@ApiOperation(value = "Lấy thông tin sản phẩm")
	public ResponseDTO loadProductById(Long id) {
		return ResponseDTO.success(this.productService.loadProductById(id));
	}

	@PostMapping("/save")
	@ApiOperation(value = "Thêm sản phẩm")
	public ResponseDTO saveProduct(@Validated @ModelAttribute CreateProductDTO product,
    	    @RequestParam(value="productFile", required = false) MultipartFile productFile) {
		return ResponseDTO.success(this.productService.saveProduct(product,productFile));
	}

	@PostMapping("/update")
	@ApiOperation(value = "Cập nhật sản phẩm")
	public ResponseDTO updateProduct(@Validated @ModelAttribute UpdateProductDTO product,
    	    @RequestParam(value="productFile", required = false) MultipartFile productFile) {
		return ResponseDTO.success(this.productService.updateProduct(product, productFile));
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
	
	@PostMapping("/update-status")
	@ApiOperation(value = "Cập nhật trạng thái sản phẩm")
	public ResponseDTO updateProductStatus(@RequestBody UpdateProductStatusDTO updateStatusDTO) {
	    return ResponseDTO.success(this.productService.updateProductStatus(updateStatusDTO));
	}
	
	@GetMapping("/statistic-product")
	@ApiOperation(value = "Thống kê số lượng sản phẩm")
	public ResponseDTO statisticProduct() {
		return ResponseDTO.success(this.productService.statisticProduct());
	}

	@GetMapping("/get-recently-added")
	@ApiOperation(value = "Lấy danh sách sản phẩm mới được thêm vào gần đây")
	public ResponseDTO getRecentlyAddedProducts() {
		return ResponseDTO.success(this.productService.getRecentlyAddedProducts(3));
	}

}
