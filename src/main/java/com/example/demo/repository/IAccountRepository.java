package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Account;

public interface IAccountRepository extends JpaRepository<Account, Long>{
	
	//Find Account by Username
	Optional<Account> findByUsername(String username);
	
	Optional<Account> findById(Long id);
	
	//Check if the username already exists or not
    Boolean existsByUsername(String username);

}
