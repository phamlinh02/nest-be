package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Account;

public interface IAccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByUsername(String username);

	Boolean existsByUsername(String username);
}
