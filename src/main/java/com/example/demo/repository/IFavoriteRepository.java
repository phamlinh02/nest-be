package com.example.demo.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Favorite;

public interface IFavoriteRepository extends JpaRepository<Favorite, Long>{

	Page<Favorite> findByAccountId(Long accountId, Pageable pageable);
	
	boolean existsByAccountIdAndProductId(Long accountId, Long productId);
	
	Optional<Favorite> findByAccountIdAndProductId(Long accountId, Long productId);
}
