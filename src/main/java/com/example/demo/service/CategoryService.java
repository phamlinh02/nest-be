package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Category;
import com.example.demo.domain.Role;
import com.example.demo.repository.ICategotyRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.dto.category.CategoryDTO;
import com.example.demo.service.dto.category.CategoryDetailDTO;
import com.example.demo.service.dto.category.CreateCategoryDTO;
import com.example.demo.service.dto.category.UpdateCategoryDTO;
import com.example.demo.service.dto.category.UpdateCategoryStatusDTO;
import com.example.demo.service.dto.role.RoleDTO;
import com.example.demo.service.dto.role.UpdateRoleStatusDTO;
import com.example.demo.service.mapper.MapperUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final ICategotyRepository categoryRepository;
	private final IProductRepository productRepository;
	
	public Page<CategoryDTO> getAllCategoryIsActive(Pageable pageable) {
	    Page<CategoryDTO> categories = MapperUtils.mapEntityPageIntoDtoPage(
	        categoryRepository.findByIsActiveTrue(pageable), CategoryDTO.class
	    );

	    List<CategoryDTO> categoriesWithProductCount = new ArrayList<>();
	    for (CategoryDTO category : categories.getContent()) {
	        Long productCount = productRepository.countByCategoryIdAndIsActiveTrue(category.getId());
	        category.setProductCount(productCount);
	        categoriesWithProductCount.add(category);
	    }

	    return new PageImpl<>(categoriesWithProductCount, pageable, categories.getTotalElements());
	}

	public long calculateTotalCatory() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream()
				.count();
	}
	
	public Page<CategoryDTO> getAllCategory(Pageable pageable) {
        Page<CategoryDTO> categories = MapperUtils.mapEntityPageIntoDtoPage(this.categoryRepository.findAll(pageable), CategoryDTO.class);
        return categories;
    }
	
	public CategoryDTO saveCategory(CreateCategoryDTO category, MultipartFile categoryFile) {
        if (this.categoryRepository.existsByName(category.getName())) {
            throw new NotFoundException("Tên danh mục đã tồn tại");
        } else {
        	Category categoryEntity = MapperUtils.map(category, Category.class);
        	String newAvatarPath = null;
    		if (categoryFile != null) {
    			try {
    				
    				String originalFileName = categoryFile.getOriginalFilename();
    				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
    				newAvatarPath = "category_" + generateRandomNumber(100, 999) + fileExtension;
    				String uploadDir = "uploads/category";
    				Path uploadPath = Paths.get(uploadDir);

    				if (!Files.exists(uploadPath)) {
    					Files.createDirectories(uploadPath);
    				}

    				Path filePath = uploadPath.resolve(newAvatarPath);
    				Files.copy(categoryFile.getInputStream(), filePath);

    				categoryEntity.setImageCategory(newAvatarPath);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
            return MapperUtils.map(this.categoryRepository.save(categoryEntity), CategoryDTO.class);
        }
    }
	
	public CategoryDTO updateCategory(UpdateCategoryDTO updateCategoryDTO, MultipartFile categoryFile) {
		Category category = this.categoryRepository.findById(updateCategoryDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục sản phẩm"));
		
		String oldImageProductPath = category.getImageCategory();

		category.setName(updateCategoryDTO.getName());
		category.setIsActive(updateCategoryDTO.getIsActive());
		String newImagePath = null;
		
		if (categoryFile != null) {
			try {
				String originalFileName = categoryFile.getOriginalFilename();
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				newImagePath = "category_" + generateRandomNumber(100, 999) + fileExtension;
				String uploadDir = "uploads/category";
				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Path filePath = uploadPath.resolve(newImagePath);
				Files.copy(categoryFile.getInputStream(), filePath);

				if (oldImageProductPath != null) {
					Path oldFilePath = uploadPath.resolve(oldImageProductPath);
					Files.delete(oldFilePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (newImagePath != null) {
			category.setImageCategory(newImagePath);
		} else {
			category.setImageCategory(oldImageProductPath);
		}

		Category categoryEntity = MapperUtils.map(category, Category.class);
		return MapperUtils.map(this.categoryRepository.save(categoryEntity), CategoryDTO.class);
	}
	
	public CategoryDetailDTO loadCategoryById(Long id) {
		Category category = this.categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục sản phẩm"));
		
		CategoryDetailDTO categoryDetailDTO = MapperUtils.map(category, CategoryDetailDTO.class);

		return categoryDetailDTO;
	}
	
	public CategoryDTO updateCategoryStatus(UpdateCategoryStatusDTO updateStatusDTO) {
		Category category = this.categoryRepository.findById(updateStatusDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục sản phẩm"));

		category.setIsActive(updateStatusDTO.isActive());

		Category updatedCategory = this.categoryRepository.save(category);

		CategoryDTO categoryDTO = MapperUtils.map(updatedCategory, CategoryDTO.class);

		return categoryDTO;
	}
	
	public int generateRandomNumber(int min, int max) {
	    return (int) (Math.random() * (max - min + 1) + min);
	}
}
