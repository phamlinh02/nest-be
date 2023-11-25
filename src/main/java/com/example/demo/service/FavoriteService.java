package com.example.demo.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Account;
import com.example.demo.domain.Category;
import com.example.demo.domain.Favorite;
import com.example.demo.domain.Product;
import com.example.demo.domain.Rate;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.ICategotyRepository;
import com.example.demo.repository.IFavoriteRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.repository.IRateRepository;
import com.example.demo.service.dto.favorite.AddProductFavoriteDTO;
import com.example.demo.service.dto.favorite.FavoriteDTO;
import com.example.demo.service.mapper.MapperUtils;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class FavoriteService {
	private final IFavoriteRepository favoriteRepository;
	private final IProductRepository productRepository;
	private final ICategotyRepository categoryRepository;
	private final IAccountRepository accountRepository;
	private final IRateRepository rateRepository;

	 public Page<FavoriteDTO> getFavoriteProducts(Long accountId, Pageable pageable) {
	        Page<Favorite> favorites = this.favoriteRepository.findByAccountId(accountId, pageable);
	        List<FavoriteDTO> favoriteDTOs = new ArrayList<>();

	        for (Favorite favorite : favorites.getContent()) {
	            Product product = this.productRepository.findById(favorite.getProductId())
	                    .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm"));

	            Category category = this.categoryRepository.findById(product.getCategoryId())
	                    .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục"));

	            Pageable ratePageable = PageRequest.of(0, 10);
	            Page<Rate> ratesPage = rateRepository.findByProductId(product.getId(), ratePageable);

	            long totalRatings = ratesPage.getTotalElements();
	            Double averageRating = totalRatings > 0 ?
	                    ratesPage.getContent().stream().mapToInt(Rate::getStar).average().orElse(0.0) : 0.0;

	            FavoriteDTO favoriteDTO = new FavoriteDTO();
	            favoriteDTO.setId(favorite.getId());
	            favoriteDTO.setAccountId(accountId);
	            favoriteDTO.setProductId(product.getId());
	            favoriteDTO.setProductName(product.getProductName());
	            favoriteDTO.setPrice(product.getPrice());
	            favoriteDTO.setQuantity(product.getQuantity());
	            favoriteDTO.setIsActive(product.getIsActive());
	            favoriteDTO.setImage(product.getImage());
	            favoriteDTO.setCategoryId(category.getId());
	            favoriteDTO.setCategoryName(category.getName());
	            favoriteDTO.setTotalRatings(totalRatings);
	            favoriteDTO.setAverageRating(averageRating);

	            favoriteDTOs.add(favoriteDTO);
	        }

	        return new PageImpl<>(favoriteDTOs, pageable, favorites.getTotalElements());
	    }

	public FavoriteDTO addProductFavorite(AddProductFavoriteDTO favorite) {
		Account account = this.accountRepository.findById(favorite.getAccountId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy tài khoản"));

		Product product = this.productRepository.findById(favorite.getProductId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm"));
		
		Category category = this.categoryRepository.findById(product.getCategoryId())
	            .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục"));

		if (favoriteRepository.existsByAccountIdAndProductId(account.getId(), product.getId())) {
			throw new IllegalArgumentException("Sản phẩm đã có trong danh sách yêu thích");
		}

		Favorite favoriteEntity = new Favorite();
		favoriteEntity.setAccountId(account.getId());
		favoriteEntity.setProductId(product.getId());
		favoriteRepository.save(favoriteEntity);
		
		 Pageable pageable = PageRequest.of(0, 10);
		    Page<Rate> ratesPage = rateRepository.findByProductId(product.getId(), pageable);

		    long totalRatings = ratesPage.getTotalElements();
		    Double averageRating = totalRatings > 0 ?
		            ratesPage.getContent().stream().mapToInt(Rate::getStar).average().orElse(0.0) : 0.0;
		
		FavoriteDTO favoriteDTO = new FavoriteDTO();
	    favoriteDTO.setId(favoriteEntity.getId());
	    favoriteDTO.setAccountId(account.getId());
	    favoriteDTO.setProductId(product.getId());
	    favoriteDTO.setProductName(product.getProductName());
	    favoriteDTO.setPrice(product.getPrice());
	    favoriteDTO.setQuantity(product.getQuantity());
	    favoriteDTO.setIsActive(product.getIsActive());
	    favoriteDTO.setImage(product.getImage());
	    favoriteDTO.setCategoryId(category.getId());
	    favoriteDTO.setCategoryName(category.getName());
	    favoriteDTO.setTotalRatings(totalRatings);
	    favoriteDTO.setAverageRating(averageRating);
	    
	    return favoriteDTO;

	}
	
	 public void removeProductFromFavorite(Long accountId, Long productId) {
	        Favorite favorite = this.favoriteRepository.findByAccountIdAndProductId(accountId, productId)
	                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm trong danh sách yêu thích"));
	        
	        favoriteRepository.delete(favorite);
	    }

}
