package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.OrderDetail;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long>{

}
