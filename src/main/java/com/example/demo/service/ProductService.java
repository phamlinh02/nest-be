package com.example.demo.service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Category;
import com.example.demo.domain.Product;
import com.example.demo.repository.ICategotyRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.dto.product.CreateProductDTO;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.dto.product.ProductDetailDTO;
import com.example.demo.service.dto.product.UpdateProductDTO;
import com.example.demo.service.mapper.MapperUtils;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final IProductRepository productRepository;
	private final ICategotyRepository categoryRepository;

	public Page<ProductDTO> getAllProduct(Pageable pageable) {
		Page<Product> productPage = this.productRepository.findAll(pageable);
		List<ProductDTO> productDTOList = new ArrayList<>();

		for (Product product : productPage.getContent()) {
			if (product.getIsActive()) {
				ProductDTO productDTO = MapperUtils.map(product, ProductDTO.class);

				Category category = this.categoryRepository.findById(product.getCategoryId()).orElse(null);
				if (category != null) {
					productDTO.setCategoryName(category.getName());
				}

				productDTOList.add(productDTO);
			}
		}

		return new PageImpl<>(productDTOList, pageable, productPage.getTotalElements());
	}

	public ProductDetailDTO loadProductById(Long id) {
		Product product = this.productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm"));
		Category category = this.categoryRepository.findById(product.getCategoryId()).orElse(null);

		ProductDetailDTO productDetailDTO = MapperUtils.map(product, ProductDetailDTO.class);

		if (category != null) {
			productDetailDTO.setCategoryName(category.getName());
		}

		return productDetailDTO;
	}

	public ProductDTO saveProduct(CreateProductDTO product) {
		if (product.getCategoryName() == null) {
			throw new NotFoundException("Không được để trống category");
		}

		Category category = this.categoryRepository.findByName(product.getCategoryName());

		if (category == null) {
			throw new NotFoundException("Không tìm thấy category");
		}

		Product productEntity = MapperUtils.map(product, Product.class);

		productEntity.setCategoryId(category.getId());

		productEntity = this.productRepository.save(productEntity);

		ProductDTO productDTO = MapperUtils.map(productEntity, ProductDTO.class);
		productDTO.setCategoryName(category.getName());

		return productDTO;

	}

	public ProductDTO updateProduct(UpdateProductDTO updateProductDTO) {
		Product product = this.productRepository.findById(updateProductDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm"));

		product.setProductName(updateProductDTO.getProductName());
		product.setPrice(updateProductDTO.getPrice());
		product.setDescription(updateProductDTO.getDescription());
		product.setImage(updateProductDTO.getImage());
		product.setIsActive(updateProductDTO.getIsActive());
		product.setQuantity(updateProductDTO.getQuantity());

		Category category = this.categoryRepository.findByName(updateProductDTO.getCategoryName());

		if (category == null) {
			throw new NotFoundException("Không tìm thấy category");
		}

		product.setCategoryId(category.getId());

		Product productEntity = MapperUtils.map(product, Product.class);
		ProductDTO productDTO = MapperUtils.map(this.productRepository.save(productEntity), ProductDTO.class);

		productDTO.setCategoryName(category.getName());

		return productDTO;
	}

	public Page<ProductDTO> searchProductsByName(String productName, Pageable pageable) {
		Page<Product> productPage = productRepository.findByProductNameContainingIgnoreCase(productName, pageable);

        Page<ProductDTO> productDTOPage = productPage.map(product -> {
            ProductDTO productDTO = MapperUtils.map(product, ProductDTO.class);

            Category category = categoryRepository.findById(product.getCategoryId()).orElse(null);
            if (category != null) {
                productDTO.setCategoryName(category.getName());
            }

            return productDTO;
        });

        return productDTOPage;
	}
	
	public Page<ProductDTO> showProductsByCategory(Long categoryId, Pageable pageable) {
		Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);

        Page<ProductDTO> productDTOPage = productPage.map(product -> {
            ProductDTO productDTO = MapperUtils.map(product, ProductDTO.class);

            Category category = categoryRepository.findById(product.getCategoryId()).orElse(null);
            if (category != null) {
                productDTO.setCategoryName(category.getName());
            }
            return productDTO;
        });

        return productDTOPage;
    }
}
