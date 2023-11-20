package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Rate;

public interface IRateRepository extends JpaRepository<Rate, Long>{
	Page<Rate> findByProductId(Long productId, Pageable pageable);

}
