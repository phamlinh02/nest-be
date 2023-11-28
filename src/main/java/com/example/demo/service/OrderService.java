package com.example.demo.service;

import com.example.demo.config.Constant;
import com.example.demo.config.exception.common.NotEnoughException;
import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Account;
import com.example.demo.domain.Bill;
import com.example.demo.domain.BillDetail;
import com.example.demo.domain.Product;
import com.example.demo.repository.*;
import com.example.demo.service.dto.account.AccountDTO;
import com.example.demo.service.dto.order.BillDTO;
import com.example.demo.service.dto.order.BillDetailDTO;
import com.example.demo.service.dto.order.StatisticsBillDTO;
import com.example.demo.service.dto.order.ViewBillDetail;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.mapper.MapperUtils;
import com.example.demo.service.util.CartItemService;
import com.example.demo.service.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final IOrderRepository iOrderRepository;
    private final IAccountRepository accountRepository;
    private final IOrderDetailRepository iOrderDetailRepository;
    private final IProductRepository productRepository;
    private final ICartItemRepository cartItemRepository;
    private final CartItemService cartItemService;

    public OrderService(
            IOrderRepository iOrderRepository,
            IOrderDetailRepository iOrderDetailRepository,
            IAccountRepository accountRepository,
            IProductRepository productRepository,
            ICartItemRepository iCartItemRepository,
            CartItemService cartItemService
    ) {
        this.iOrderDetailRepository = iOrderDetailRepository;
        this.iOrderRepository = iOrderRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = iCartItemRepository;
        this.cartItemService = cartItemService;

    }

    public Page<BillDTO> getAllOrder(Pageable pageable, BillDTO billDTO) {
        return this.iOrderRepository.getAllOrder(pageable, billDTO);
    }

    public BillDTO updateBill(BillDTO dto) {
        Bill bill = this.iOrderRepository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        bill.setStatus(dto.getStatus());
        bill.setDescription(dto.getDescription());
        return MapperUtils.map(this.iOrderRepository.save(bill), BillDTO.class);
    }

    public void createBill(ViewBillDetail cartCheckout) {
        //save bill
        Bill bill = new Bill();
        BillDTO billDTO = cartCheckout.getBill();

        Account account = this.accountRepository.findByUsername(cartCheckout.getAccount().getUsername()).orElseGet(() -> {
            cartCheckout.getAccount().setPassword("AA1234aa");
            return this.accountRepository.save(MapperUtils.map(cartCheckout.getAccount(), Account.class));
        });

        if (!DataUtils.isNullObject(billDTO.getId())) {
            bill = this.iOrderRepository.findById(billDTO.getId()).orElseThrow(NotFoundException::new);
        }
        bill.setAccountId(account.getId());
        bill.setStaffId(billDTO.getStaffId());
        bill.setOrderDate(new Date());
        bill.setDescription(billDTO.getDescription());
        bill.setStatus(Constant.BILL_STATUS.NEW);

        Bill finalBill = this.iOrderRepository.save(bill);

        //save bill detail

        cartCheckout.getBillDetails().forEach(detail -> {
            Product product = this.productRepository.findById(detail.getProductId()).orElseThrow(NotFoundException::new);

            if (!product.getIsActive()) {
                throw new NotFoundException();
            }
            if (product.getQuantity() < detail.getQuantity()) {
                throw new NotEnoughException();
            }

            product.setQuantity(product.getQuantity() - detail.getQuantity());

            this.productRepository.save(product);

            detail.setOrderId(finalBill.getId());
            detail.setPrice(product.getPrice());
            this.iOrderDetailRepository.save(MapperUtils.map(detail, BillDetail.class));
        });

        this.cartItemService.remove(bill.getAccountId());
    }

    public ViewBillDetail getBillDetail(Long billID) {
        ViewBillDetail result = new ViewBillDetail();
        Bill bill = this.iOrderRepository.findById(billID).orElseThrow(NotFoundException::new);
        result.setBill(MapperUtils.map(bill, BillDTO.class));

        Account account = this.accountRepository.findById(bill.getAccountId()).orElseThrow(NotFoundException::new);
        result.setAccount(MapperUtils.map(account, AccountDTO.class));

        List<BillDetailDTO> list = MapperUtils.mapList(this.iOrderDetailRepository.findAllByOrderId(billID), BillDetailDTO.class);
        list.forEach(detail -> {
            ProductDTO dto = MapperUtils.map(this.productRepository.findById(detail.getProductId()).orElseThrow(NotFoundException::new), ProductDTO.class);
            detail.setProductDTO(dto);
        });
        result.setBillDetails(list);
        return result;
    }

    public StatisticsBillDTO getStatisticsBill() {

        return StatisticsBillDTO.builder().totalBill(
                        MapperUtils.mapList(this.iOrderRepository.findAllByOrderByOrderDateDesc(), BillDTO.class)
                )
                .completeBill(
                        MapperUtils.mapList(this.iOrderRepository.findAllByStatusEqualsOrderByOrderDate(Constant.BILL_STATUS.COMPLETED), BillDTO.class)
                )
                .newBill(
                        MapperUtils.mapList(this.iOrderRepository.findAllByStatusEqualsOrderByOrderDate(Constant.BILL_STATUS.NEW), BillDTO.class)
                ).cancelBill(
                        MapperUtils.mapList(this.iOrderRepository.findAllByStatusEqualsOrderByOrderDate(Constant.BILL_STATUS.CANCELLED), BillDTO.class)
                )
                .build();

    }

    public List<BillDTO> getListBill(){
        return MapperUtils.mapList(this.iOrderRepository.findAllByOrderByOrderDateDesc(), BillDTO.class);
    }
    
    public void saveEntity(BillDTO bill) {
    	this.iOrderRepository.save(MapperUtils.map(bill, Bill.class));
    }

}
