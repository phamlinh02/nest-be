package com.example.demo.web.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.RateService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.product.UpdateProductDTO;
import com.example.demo.service.dto.rate.CreateRateDTO;
import com.example.demo.service.dto.rate.UpdateRateDTO;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RequestMapping("/api/nest/rate")
@AllArgsConstructor
@RestController 
@CrossOrigin("*")
public class RateResource {
	
	private final RateService rateService;

	@GetMapping("/get-all")
    @ApiOperation(value = "Lấy danh sách đánh giá")
    public ResponseDTO getAllRate(Pageable pageable) {
        return ResponseDTO.success(this.rateService.getAllRate(pageable));
    }
	
	@GetMapping("/get-rate")
	@ApiOperation(value = "Lấy thông tin đánh giá")
	public ResponseDTO loadRateById(Long id) {
		return ResponseDTO.success(this.rateService.loadRateById(id));
	}
	
	@PostMapping("/save")
	@ApiOperation(value = "Tạo đánh giá")
	public ResponseDTO createRate(@Validated @ModelAttribute CreateRateDTO rate,
			@RequestParam(value="rateFile", required = false) MultipartFile rateFile) {
		return ResponseDTO.success(this.rateService.createRate(rate,rateFile));
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "Cập nhật đánh giá")
	public ResponseDTO updateProduct(@Validated @RequestBody UpdateRateDTO rate) {
		return ResponseDTO.success(this.rateService.updateRate(rate));
	}
	
	@GetMapping("/show-by-productId")
	@ApiOperation(value = "Hiển thị đánh giá theo sản phẩm")
	public ResponseDTO showRatesByProductId(@RequestParam Long productId, Pageable pageable) {
		return ResponseDTO.success(this.rateService.showRatesByProductId(productId, pageable));
	}
	
	@DeleteMapping("/delete/{rateId}")
    @ApiOperation(value = "Xóa đánh giá theo ID")
    public ResponseDTO deleteRate(@PathVariable Long rateId) {
        this.rateService.deleteRate(rateId);
        return ResponseDTO.success("Xóa đánh giá thành công");
    }
	
	@GetMapping("/get-statistics")
	@ApiOperation(value = "Lấy thống kê đánh giá theo sản phẩm")
	@PreAuthorize("hasRole('ADMIN') or hasRole('DIRECTOR')")
	public ResponseDTO getRateStatistics(Pageable pageable) {
	    return ResponseDTO.success(this.rateService.getRateStatistics());
	}

	@GetMapping("/get-top-rated-products")
	@ApiOperation(value = "Lấy danh sách sản phẩm có đánh giá cao nhất")
	public ResponseDTO getTopRatedProducts() {
		return ResponseDTO.success(this.rateService.getTopRatedProducts(3));
	}
	
}
