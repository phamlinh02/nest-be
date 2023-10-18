package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Authority;

public interface IAuthorityRepository extends JpaRepository<Authority, Long> {
	List<Authority> findByAccountId(Long accountId);

	void deleteByAccountId(Long accountId);
}
