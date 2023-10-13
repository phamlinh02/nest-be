package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Account;
import com.example.demo.domain.Authority;

public interface IAuthorityRepository extends JpaRepository<Authority, Long>{
	void deleteByAccount(Account account);
}
