package com.example.demo.service;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Bill;
import com.example.demo.domain.BillDetail;
import com.example.demo.domain.Product;
import com.example.demo.repository.IOrderDetailRepository;
import com.example.demo.repository.IOrderRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.dto.order.BillDTO;
import com.example.demo.service.mapper.MapperUtils;
import com.example.demo.service.util.DataUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final IOrderRepository iOrderRepository;
    private final IOrderDetailRepository iOrderDetailRepository;

    private final IProductRepository iProductReponsitory;

    public OrderService(
            IOrderRepository iOrderRepository,
            IOrderDetailRepository iOrderDetailRepository,
            IProductRepository iProductReponsitory) {
        this.iOrderDetailRepository = iOrderDetailRepository;
        this.iOrderRepository = iOrderRepository;
        this.iProductReponsitory = iProductReponsitory;
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
                    Optional<Product> productDetails = iProductReponsitory.findById(entry.getKey());
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
