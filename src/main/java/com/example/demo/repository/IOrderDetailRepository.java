package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.BillDetail;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<BillDetail, Long>{

    List<BillDetail> findAllByOrderId(Long orderId);
}
