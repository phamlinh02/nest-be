package com.example.demo.service;

import com.example.demo.config.Constant;
import com.example.demo.config.exception.common.NotComparePriceException;
import com.example.demo.config.exception.common.NotEnoughException;
import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Account;
import com.example.demo.domain.Bill;
import com.example.demo.domain.BillDetail;
import com.example.demo.domain.Product;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.IOrderDetailRepository;
import com.example.demo.repository.IOrderRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.account.AccountDTO;
import com.example.demo.service.dto.order.*;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.mapper.MapperUtils;
import com.example.demo.service.util.DataUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Product> getTopSellingProducts(int limit) {
        // Assuming you have a service or repository to retrieve all bill details
        List<BillDetail> allBillDetails = iOrderDetailRepository.findAll(); // Replace with actual service/repository

        // Group bill details by product and sum the quantities
        Map<Long, Long> productQuantities = allBillDetails.stream()
                .collect(Collectors.groupingBy(BillDetail::getProductId,
                        Collectors.summingLong(BillDetail::getQuantity)));

        // Sort products by total quantity in descending order
        List<Product> sortedProducts = productQuantities.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> {
                    Product product = new Product();
                    product.setId(entry.getKey()); // Assuming Product has an 'id' field

                    // Fetch additional details from your Product entity based on the ID
                    // Replace with actual logic to retrieve Product by ID
                    Optional<Product> productDetails = productRepository.findById(entry.getKey());
                    product.setId(productDetails.get().getId());
                    product.setProductName(productDetails.get().getProductName());
                    product.setDescription(productDetails.get().getDescription());
                    product.setPrice(productDetails.get().getPrice());
                    product.setImage(productDetails.get().getImage());
                    product.setCategoryId(productDetails.get().getCategoryId());
                    // Add other fields as needed

                    return product;
                })
                .collect(Collectors.toList());

        return sortedProducts;
    }

}