package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Rate;

public interface IRateRepository extends JpaRepository<Rate, Long>{

}
