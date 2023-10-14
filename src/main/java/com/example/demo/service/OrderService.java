package com.example.demo.service;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Bill;
import com.example.demo.repository.IOrderDetailRepository;
import com.example.demo.repository.IOrderRepository;
import com.example.demo.service.dto.order.BillDTO;
import com.example.demo.service.mapper.MapperUtils;
import com.example.demo.service.util.DataUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final IOrderRepository iOrderRepository;
    private final IOrderDetailRepository iOrderDetailRepository;

    public OrderService(
            IOrderRepository iOrderRepository,
            IOrderDetailRepository iOrderDetailRepository
    ) {
        this.iOrderDetailRepository = iOrderDetailRepository;
        this.iOrderRepository = iOrderRepository;
    }

    public Page<BillDTO> getAllOrder(Pageable pageable, BillDTO billDTO) {
        return this.iOrderRepository.getAllOrder(pageable, billDTO);
    }

    public BillDTO updateStatusBill(BillDTO dto){
        Bill bill = this.iOrderRepository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        bill.setStatus(dto.getStatus());
        return MapperUtils.map(this.iOrderRepository.save(bill), BillDTO.class);
    }

    public  BillDTO createBill( BillDTO dto){
        Bill bill = new Bill();
        if(!DataUtils.isNullObject(dto.getId())){
            bill = this.iOrderRepository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        }
        // logic save bill/ bill detail and decrease quantity product
        return MapperUtils.map(bill, BillDTO.class);
    }

}
