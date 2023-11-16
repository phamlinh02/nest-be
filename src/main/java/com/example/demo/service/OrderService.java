package com.example.demo.service;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Account;
import com.example.demo.domain.Bill;
import com.example.demo.domain.BillDetail;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.IOrderDetailRepository;
import com.example.demo.repository.IOrderRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.dto.account.AccountDTO;
import com.example.demo.service.dto.order.*;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.mapper.MapperUtils;
import com.example.demo.service.util.DataUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final IOrderRepository iOrderRepository;
    private final IAccountRepository accountRepository;
    private final IOrderDetailRepository iOrderDetailRepository;
    private final IProductRepository productRepository;

    public OrderService(
            IOrderRepository iOrderRepository,
            IOrderDetailRepository iOrderDetailRepository,
            IAccountRepository accountRepository,
            IProductRepository productRepository
    ) {
        this.iOrderDetailRepository = iOrderDetailRepository;
        this.iOrderRepository = iOrderRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
    }

    public Page<BillDTO> getAllOrder(Pageable pageable, BillDTO billDTO) {
        return this.iOrderRepository.getAllOrder(pageable, billDTO);
    }

    public BillDTO updateStatusBill(BillDTO dto) {
        Bill bill = this.iOrderRepository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        bill.setStatus(dto.getStatus());
        return MapperUtils.map(this.iOrderRepository.save(bill), BillDTO.class);
    }

    public BillDTO createBill(BillDTO dto) {
        Bill bill = new Bill();
        if (!DataUtils.isNullObject(dto.getId())) {
            bill = this.iOrderRepository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        }
        // logic save bill/ bill detail and decrease quantity product
        return MapperUtils.map(bill, BillDTO.class);
    }

    public ViewBillDetail getBillDetail(Long billID) {
        ViewBillDetail result = new ViewBillDetail();
        Bill bill = this.iOrderRepository.findById(billID).orElseThrow(NotFoundException::new);
        result.setBill(MapperUtils.map(bill, BillDTO.class));

        Account account = this.accountRepository.findById(bill.getAccountId()).orElseThrow(NotFoundException::new);
        result.setAccount(MapperUtils.map(account, AccountDTO.class));

        List<BillDetailDTO> list = MapperUtils.mapList(this.iOrderDetailRepository.findAllByOrderId(billID), BillDetailDTO.class);
        list.forEach(detail ->{
                ProductDTO dto = MapperUtils.map(this.productRepository.findById(detail.getProductId()).orElseThrow(NotFoundException::new), ProductDTO.class);
                detail.setProductDTO(dto);
        });
        result.setBillDetails(list);
        return result;
    }

}
