package com.example.demo.repository;

import com.example.demo.config.Constant;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Bill;

import java.util.Date;
import java.util.List;

public interface IOrderRepository extends JpaRepository<Bill, Long>, IOrderRepositoryCustom {
    List<Bill> findByOrderDateBetween(Date startDate, Date endDate);

    List<Bill> findAllByStatusEqualsOrderByOrderDate(Constant.BILL_STATUS status);
    List<Bill> findAllByOrderByOrderDateDesc();

}
