package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Product;

public interface IProductRepository extends JpaRepository<Product, Long>{
	
}
