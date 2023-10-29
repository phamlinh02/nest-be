package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Category;
import com.example.demo.repository.ICategotyRepository;
import com.example.demo.service.dto.category.CategoryDTO;
import com.example.demo.service.dto.category.CreateCategoryDTO;
import com.example.demo.service.dto.category.UpdateCategoryDTO;
import com.example.demo.service.mapper.MapperUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final ICategotyRepository categoryRepository;
	
	public Page<CategoryDTO> getAllCategory(Pageable pageable) {
        Page<CategoryDTO> categories = MapperUtils.mapEntityPageIntoDtoPage(this.categoryRepository.findByIsActiveTrue(pageable), CategoryDTO.class);
        return categories;
    }
	
	public CategoryDTO saveCategory(CreateCategoryDTO category) {
        if (this.categoryRepository.existsByName(category.getName())) {
            throw new NotFoundException("Tên danh mục đã tồn tại");
        } else {
            Category categoryEntity = MapperUtils.map(category, Category.class);
            return MapperUtils.map(this.categoryRepository.save(categoryEntity), CategoryDTO.class);
        }
    }
	
	public CategoryDTO updateCategory(UpdateCategoryDTO updateCategoryDTO) {
		Category category = this.categoryRepository.findById(updateCategoryDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục sản phẩm"));

		category.setName(updateCategoryDTO.getName());
		category.setIsActive(updateCategoryDTO.getIsActive());

		Category categoryEntity = MapperUtils.map(category, Category.class);
		return MapperUtils.map(this.categoryRepository.save(categoryEntity), CategoryDTO.class);
	}
}
