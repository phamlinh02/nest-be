package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Category;

public interface ICategotyRepository extends JpaRepository<Category, Long>{

}
