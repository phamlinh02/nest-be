package com.example.demo.service;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Category;
import com.example.demo.domain.Product;
import com.example.demo.repository.ICategotyRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.dto.product.CreateProductDTO;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.dto.product.ProductDetailDTO;
import com.example.demo.service.dto.product.UpdateProductDTO;
import com.example.demo.service.dto.product.UpdateProductStatusDTO;
import com.example.demo.service.mapper.MapperUtils;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final IProductRepository productRepository;
	private final ICategotyRepository categoryRepository;

	public Product findById(Long id) {
		return productRepository.findById(id).get();
	}

	public Page<ProductDTO> getAllProductIsActive(Pageable pageable) {
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

	public Page<ProductDTO> getAllProduct(Pageable pageable) {
	    Page<Product> productPage = this.productRepository.findAll(pageable);
	    List<ProductDTO> productDTOList = new ArrayList<>();

	    for (Product product : productPage.getContent()) {
	        Category category = this.categoryRepository.findById(product.getCategoryId()).orElse(null);

	        if (category == null || category.getIsActive()) {
	            ProductDTO productDTO = MapperUtils.map(product, ProductDTO.class);

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

	public ProductDTO saveProduct(CreateProductDTO product, MultipartFile productFile) {
		if (product.getCategoryName() == null) {
			throw new NotFoundException("Không được để trống category");
		}

		Category category = this.categoryRepository.findByName(product.getCategoryName());

		if (category == null) {
			throw new NotFoundException("Không tìm thấy category");
		}

		Product productEntity = MapperUtils.map(product, Product.class);

		String newAvatarPath = null;
		if (productFile != null) {
			try {
				String originalFileName = productFile.getOriginalFilename();
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				newAvatarPath = "product_" + generateRandomNumber(100, 999) + fileExtension;
				String uploadDir = "uploads/product";
				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Path filePath = uploadPath.resolve(newAvatarPath);
				Files.copy(productFile.getInputStream(), filePath);

				productEntity.setImage(newAvatarPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		productEntity.setCategoryId(category.getId());

		productEntity = this.productRepository.save(productEntity);

		ProductDTO productDTO = MapperUtils.map(productEntity, ProductDTO.class);
		productDTO.setCategoryName(category.getName());

		return productDTO;

	}

	public ProductDTO updateProduct(UpdateProductDTO updateProductDTO, MultipartFile productFile) {
		Product product = this.productRepository.findById(updateProductDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm"));

		String oldImageProductPath = product.getImage();

		product.setProductName(updateProductDTO.getProductName());
		product.setPrice(updateProductDTO.getPrice());
		product.setDescription(updateProductDTO.getDescription());
		product.setIsActive(updateProductDTO.getIsActive());
		product.setQuantity(updateProductDTO.getQuantity());
		String newImagePath = null;
		if (productFile != null) {
			try {
				String originalFileName = productFile.getOriginalFilename();
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				newImagePath = "product_" + generateRandomNumber(100, 999) + fileExtension;
				String uploadDir = "uploads/product";
				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Path filePath = uploadPath.resolve(newImagePath);
				Files.copy(productFile.getInputStream(), filePath);

				if (oldImageProductPath != null) {
					Path oldFilePath = uploadPath.resolve(oldImageProductPath);
					Files.delete(oldFilePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (newImagePath != null) {
			product.setImage(newImagePath);
		} else {
			product.setImage(oldImageProductPath);
		}

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
		// Tìm kiếm sản phẩm theo tên
		Page<Product> productPage = productRepository.findByProductNameContainingIgnoreCase(productName, pageable);

		// Lấy danh sách sản phẩm và thực hiện các xử lý khác
		List<ProductDTO> productDTOs = productPage.getContent().stream()
				.map(product -> {
//					// Tăng số lần tìm kiếm của sản phẩm
//					product.incrementSearchCount();
//					// Lưu lại sản phẩm với số lần tìm kiếm đã được cập nhật
//					productRepository.save(product);

					// Tiếp tục xử lý khác và tạo đối tượng ProductDTO
					ProductDTO productDTO = MapperUtils.map(product, ProductDTO.class);
					Category category = categoryRepository.findById(product.getCategoryId()).orElse(null);
					if (category != null) {
						productDTO.setCategoryName(category.getName());
					}
					return productDTO;
				}).collect(Collectors.toList());

		// Tạo trang ProductDTO từ danh sách sản phẩm
		Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());

		return productDTOPage;
	}

	public Page<ProductDTO> showProductsByCategory(Long categoryId, Pageable pageable) {
		Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);

		List<ProductDTO> activeProducts = productPage.getContent().stream().filter(product -> product.getIsActive())
				.map(product -> {
					ProductDTO productDTO = MapperUtils.map(product, ProductDTO.class);
					Category category = categoryRepository.findById(product.getCategoryId()).orElse(null);
					if (category != null) {
						productDTO.setCategoryName(category.getName());
					}
					return productDTO;
				}).collect(Collectors.toList());

		Page<ProductDTO> productDTOPage = new PageImpl<>(activeProducts, pageable, activeProducts.size());

		return productDTOPage;
	}

	public ProductDTO updateProductStatus(UpdateProductStatusDTO updateStatusDTO) {
		Product product = this.productRepository.findById(updateStatusDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm"));

		product.setIsActive(updateStatusDTO.isActive());

		Product updatedProduct = this.productRepository.save(product);

		ProductDTO productDTO = MapperUtils.map(updatedProduct, ProductDTO.class);

		return productDTO;
	}
	
	public int generateRandomNumber(int min, int max) {
	    return (int) (Math.random() * (max - min + 1) + min);
	}

	public ProductDTO convertToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setProductName(product.getProductName());
		productDTO.setPrice(product.getPrice());
		productDTO.setCategoryId(String.valueOf(product.getCategoryId()));
		productDTO.setImage(product.getImage());
		// Set other fields as needed
		return productDTO;
	}

	public List<ProductDTO> getMostSearchedProducts(int limit) {
		// Sắp xếp sản phẩm theo số lần tìm kiếm giảm dần và giới hạn số lượng sản phẩm
		List<Product> mostSearchedProducts = productRepository.findAll(
						PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "searchCount")))
				.getContent();

		// Ánh xạ danh sách sản phẩm sang danh sách DTO
		List<ProductDTO> mostSearchedProductDTOs = mostSearchedProducts.stream()
				.map(product -> {
					ProductDTO productDTO = MapperUtils.map(product, ProductDTO.class);
					Category category = categoryRepository.findById(product.getCategoryId()).orElse(null);
					if (category != null) {
						productDTO.setCategoryName(category.getName());
					}
					return productDTO;
				}).collect(Collectors.toList());

		return mostSearchedProductDTOs;
	}

}