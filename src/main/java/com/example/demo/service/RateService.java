package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Account;
import com.example.demo.domain.Category;
import com.example.demo.domain.Product;
import com.example.demo.domain.Rate;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.repository.IRateRepository;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.dto.product.ProductDetailDTO;
import com.example.demo.service.dto.product.UpdateProductDTO;
import com.example.demo.service.dto.rate.CreateRateDTO;
import com.example.demo.service.dto.rate.RateDTO;
import com.example.demo.service.dto.rate.RateDetailDTO;
import com.example.demo.service.dto.rate.RateStatisticsDTO;
import com.example.demo.service.dto.rate.UpdateRateDTO;
import com.example.demo.service.mapper.MapperUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateService {
	private final IRateRepository rateRepository;
	private final IProductRepository productRepository;
	private final IAccountRepository accountRepository;

	public Page<RateDTO> getAllRate(Pageable pageable) {
	    Page<RateDTO> rates = MapperUtils.mapEntityPageIntoDtoPage(
	        this.rateRepository.findAll(pageable).map(rate -> {
	            Account account = accountRepository.findById(rate.getAccountId()).orElse(null);
	            Product product = productRepository.findById(rate.getProductId()).orElse(null);
	            return RateDTO.builder()
	                .id(rate.getId())
	                .productId(rate.getProductId())
	                .accountId(rate.getAccountId())
	                .productName(product != null ? product.getProductName() : null)
	                .accountName(account != null ? account.getUsername() : null)
	                .accountImage(account != null ? account.getAvatar() : null)
	                .rateDate(rate.getRateDate())
	                .star(rate.getStar())
	                .comment(rate.getComment())
	                .image(rate.getImage())
	                .build();
	        }),
	        RateDTO.class);

	    return rates;
	}
	
	public RateDetailDTO loadRateById(Long id) {
		Rate rate = this.rateRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy đánh giá"));
		Account account = this.accountRepository.findById(rate.getAccountId()).orElse(null);
		Product product = this.productRepository.findById(rate.getProductId()).orElse(null);

		RateDetailDTO rateDetailDTO = MapperUtils.map(rate, RateDetailDTO.class);

		if (account != null) {
			rateDetailDTO.setAccountName(account.getUsername());
			rateDetailDTO.setAccountImage(account.getAvatar());
		}
		if(product != null) {
			rateDetailDTO.setProductName(product.getProductName());
			rateDetailDTO.setProductImage(product.getImage());
		}

		return rateDetailDTO;
	}

	public RateDTO createRate(CreateRateDTO rate, MultipartFile rateFile) {
		if (!accountRepository.existsById(rate.getAccountId()) || !productRepository.existsById(rate.getProductId())) {
	        throw new RuntimeException("Tài khoản hoặc sản phẩm không tồn tại!");
	    }
		Rate rateEntity = MapperUtils.map(rate, Rate.class);
		rateEntity.setRateDate(new Date());
		String newImagePath = null;
		if (rateFile != null) {
			try {
				
				String originalFileName = rateFile.getOriginalFilename();
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				newImagePath = "rate_" + generateRandomNumber(100, 999) + fileExtension;
				String uploadDir = "uploads/rate";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				Path filePath = uploadPath.resolve(newImagePath);
				Files.copy(rateFile.getInputStream(), filePath);
				rateEntity.setImage(newImagePath);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		rateEntity = rateRepository.save(rateEntity);
		
		RateDTO rateDTO = MapperUtils.map(rateEntity, RateDTO.class);
		
		
		return rateDTO;
	}
	
	public RateDTO updateRate(UpdateRateDTO updateRateDTO) {
		Rate rate = this.rateRepository.findById(updateRateDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy đánh giá"));
		
		rate.setRateDate(new Date());
		rate.setComment(updateRateDTO.getComment());
		rate.setStar(updateRateDTO.getStar());
		rate.setImage(updateRateDTO.getImage());


		Rate rateEntity = MapperUtils.map(rate, Rate.class);
		RateDTO productDTO = MapperUtils.map(this.rateRepository.save(rateEntity), RateDTO.class);

		return productDTO;
	}
	public Page<RateDTO> showRatesByProductId(Long productId, Pageable pageable) {
        Page<RateDTO> rates = MapperUtils.mapEntityPageIntoDtoPage(
                this.rateRepository.findByProductId(productId, pageable).map(rate -> {
                    Account account = accountRepository.findById(rate.getAccountId()).orElse(null);
                    Product product = productRepository.findById(rate.getProductId()).orElse(null);
                    return RateDTO.builder()
                            .id(rate.getId())
                            .productId(rate.getProductId())
                            .accountId(rate.getAccountId())
                            .productName(product != null ? product.getProductName(): null)
                            .accountName(account != null ? account.getUsername() : null)
                            .accountImage(account !=null ? account.getAvatar() : null)
                            .rateDate(rate.getRateDate())
                            .star(rate.getStar())
                            .comment(rate.getComment())
                            .image(rate.getImage())
                            .build();
                }),
                RateDTO.class);
        return rates;
    }
	public int generateRandomNumber(int min, int max) {
	    return (int) (Math.random() * (max - min + 1) + min);
	}
	public void deleteRate(Long rateId) {
        Rate rate = this.rateRepository.findById(rateId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đánh giá"));

        this.rateRepository.delete(rate);
    }
	
	public RateStatisticsDTO getRateStatistics() {
	    List<Rate> rates = rateRepository.findAll();

	    int totalRates = (int) rateRepository.count(); // Total count without pagination
	    double averageStar = calculateAverageStar(rates);
	    int fiveStarCount = countRatesByStar(rates, 5);
	    int fourStarCount = countRatesByStar(rates, 4);
	    int threeStarCount = countRatesByStar(rates, 3);
	    int twoStarCount = countRatesByStar(rates, 2);
	    int oneStarCount = countRatesByStar(rates, 1);

	    return new RateStatisticsDTO(
	        totalRates,
	        averageStar,
	        fiveStarCount,
	        fourStarCount,
	        threeStarCount,
	        twoStarCount,
	        oneStarCount
	    );
	}
	
	private double calculateAverageStar(List<Rate> rates) {
        if (rates.isEmpty()) {
            return 0.0;
        }

        int totalStars = rates.stream().mapToInt(Rate::getStar).sum();
        return (double) totalStars / rates.size();
    }

    private int countRatesByStar(List<Rate> rates, int star) {
        return (int) rates.stream().filter(rate -> rate.getStar() == star).count();
    }

	public List<ProductDTO> getTopRatedProducts(int limit) {
		List<ProductDTO> topProducts = productRepository.findAll().stream()
				.map(product -> {
					double averageRating = calculateAverageRating(product.getId());
					return ProductDTO.builder()
							.id(product.getId())
							.productName(product.getProductName())
							.averageRating(averageRating)
							.build();
				})
				.sorted(Comparator.comparingDouble(ProductDTO::getAverageRating).reversed())
				.limit(limit)
				.collect(Collectors.toList());

		return topProducts;
	}

	private double calculateAverageRating(Long productId) {
		List<Rate> productRates = rateRepository.findByProductId(productId);
		if (productRates.isEmpty()) {
			return 0.0;
		}

		int totalStars = productRates.stream().mapToInt(Rate::getStar).sum();
		return (double) totalStars / productRates.size();
	}
}
