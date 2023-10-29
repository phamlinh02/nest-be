package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Authority;

public interface IAuthorityRepository extends JpaRepository<Authority, Long> {

	// Find Authority by Account ID
	List<Authority> findByAccountId(Long accountId);

	// Delete Authority By Account ID
	void deleteByAccountId(Long accountId);
}
