package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.config.Constant;
import com.example.demo.domain.Role;

public interface IRoleRepository extends JpaRepository<Role, Long>{
	Role findByRoleName(Constant.ROLE_USER rolename);
	
	boolean existsByRoleName(Constant.ROLE_USER roleName);
	
	Page<Role> findByIsActiveTrue(Pageable pageable);
}
