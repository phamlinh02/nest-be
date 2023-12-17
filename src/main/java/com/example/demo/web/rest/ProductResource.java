package com.example.demo.web.rest;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.product.CreateProductDTO;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.dto.product.UpdateProductDTO;
import com.example.demo.service.dto.product.UpdateProductStatusDTO;
import com.example.demo.service.util.CartItemService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/nest/product")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductResource {
	private final ProductService productService;
	
	private final CartItemService cartItemSevice;

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
	@PreAuthorize("hasRole('ADMIN') or hasRole('DIRECTOR')")
	@ApiOperation(value = "Thống kê số lượng sản phẩm")
	public ResponseDTO statisticProduct() {
		return ResponseDTO.success(this.productService.statisticProduct());
	}
	

	@GetMapping("/get-recently-added")
	@ApiOperation(value = "Lấy danh sách sản phẩm mới được thêm vào gần đây")
	public ResponseDTO getRecentlyAddedProducts(Pageable page) {
		return ResponseDTO.success(this.productService.getRecentlyAddedProducts(3,page));
	}

	@GetMapping("/get-recently")
	@ApiOperation(value = "Lấy danh sách sản phẩm mới được thêm vào gần đây")
	public ResponseDTO getRecentlyAddedProduct(Pageable page) {
		return ResponseDTO.success(this.productService.getRecentlyAddedProducts(10,page));
	}

	@GetMapping("/get-most-searched-products")
	@ApiOperation(value = "Lấy danh sách sản phẩm có số lượt tìm kiếm nhiều nhất")
	public ResponseDTO getMostSearchedProducts(Pageable page) {
		return ResponseDTO.success(this.productService.getMostSearchedProducts(3,page));
	}
	
	@GetMapping("/top-rated-products")
	@ApiOperation(value = "Lấy danh sách 10 sản phẩm có đánh giá sao cao nhất")
	public ResponseDTO getTopRatedProducts(int limit) {
	    return ResponseDTO.success(this.productService.getTopRatedProducts(limit));
	}
	
	 @GetMapping("/selling")
	    public ResponseDTO getTopSellingProducts() {
	        try {
	            List<ProductDTO> order = productService.getTopSellingProducts(3);
	            return ResponseDTO.success(order);
	        } catch (Exception e) {
	            return ResponseDTO.error();
	        }
	    }
	 
	 @GetMapping("/top_popular")
	    public ResponseDTO getTop10ProductPopular(){
	        try {
	            List<Product> cartItems = cartItemSevice.getTopProductsAcrossAccounts(10);
	            return ResponseDTO.success(cartItems);
	        } catch (Exception e) {
	            return ResponseDTO.error();
	        }
	    }

}
