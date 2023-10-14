package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.BillDetail;

public interface IOrderDetailRepository extends JpaRepository<BillDetail, Long>{

}
