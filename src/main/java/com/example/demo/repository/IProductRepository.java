package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Product;

public interface IProductRepository extends JpaRepository<Product, Long>{
	
	//Display products list by Id
	Optional<Product> findById(Long id);
	
	//Find Product by username
	Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);
	
	//Display products list by categoryId
	Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
	
	
}
