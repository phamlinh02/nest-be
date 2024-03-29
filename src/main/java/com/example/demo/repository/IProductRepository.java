package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Category;
import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.Query;

public interface IProductRepository extends JpaRepository<Product, Long>{
	
	//Display products list by Id
	Optional<Product> findById(Long id);

	@Query("SELECT o FROM Product o WHERE o.isActive = TRUE AND o.id = ?1")
	Optional<Product> findByIdIsActive(Long id);
	//Find Product by username
	Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);
	
	//Display products list by categoryId
	Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
	
	Long countByCategoryIdAndIsActiveTrue(Long categoryId);
	
	List<Product> findByIsActive(Boolean isActive);

	List<Product> findByCategoryIdAndIsActive(Long categoryId, Boolean isActive);

	List<Product> findByIsActiveTrue();

	
}
