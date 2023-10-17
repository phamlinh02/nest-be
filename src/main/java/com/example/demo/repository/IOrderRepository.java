package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Bill;

public interface IOrderRepository extends JpaRepository<Bill, Long>, IOrderRepositoryCustom{

}
