package com.example.demo.repository;

import com.example.demo.service.dto.order.BillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderRepositoryCustom {

    Page<BillDTO> getAllOrder(Pageable pageable, BillDTO dto);
}
