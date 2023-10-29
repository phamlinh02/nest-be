package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.config.Constant;
import com.example.demo.domain.Role;

public interface IRoleRepository extends JpaRepository<Role, Long>{
	
	//Find Role by rolename
	Role findByRoleName(Constant.ROLE_USER rolename);

}
