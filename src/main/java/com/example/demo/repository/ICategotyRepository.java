package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Category;

public interface ICategotyRepository extends JpaRepository<Category, Long>{
	
	//Find Category By CategoryName
	Category findByName(String categoryName);
	
	//Check if the category name already exists or not
	Boolean existsByName(String name);
	
	//Show category list using IsActive
	Page<Category> findByIsActiveTrue(Pageable pageable);
	
}
