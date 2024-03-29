package com.example.demo.web.rest;

import com.example.demo.domain.Product;
import com.example.demo.service.OrderService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.order.BillDTO;
import com.example.demo.service.dto.order.TransactionDTO;
import com.example.demo.service.dto.order.ViewBillDetail;
import com.example.demo.service.dto.product.ProductDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/nest/order")
@RestController
@CrossOrigin("*")
public class OrderResource {

    private final OrderService orderService;

    OrderResource(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/get-all")
    public ResponseDTO getAllOrder(@RequestBody BillDTO billDTO,
                                   Pageable pageable

    ) {
        return ResponseDTO.success(this.orderService.getAllOrder(pageable, billDTO));
    }

    @PostMapping("/update")
    public ResponseDTO updateBill(@RequestBody BillDTO dto) {
        return ResponseDTO.success(this.orderService.updateBill(dto));
    }


    @PostMapping("/save")
    public ResponseDTO saveBill(@RequestBody BillDTO dto) {
    	this.orderService.saveEntity(dto);
        return ResponseDTO.success();
    }

    
    @GetMapping("/get-detail/{id}")
    public ResponseDTO getDetailBill(@PathVariable Long id) {
        return ResponseDTO.success(this.orderService.getBillDetail(id));
    }

    @PostMapping("/create-bill")
    public ResponseDTO createBill(@RequestBody ViewBillDetail cart) {
        return this.orderService.createBill(cart);
    }

//    @GetMapping("/selling")
//    public ResponseDTO getTopSellingProducts() {
//        try {
//            List<ProductDTO> order = orderService.getTopSellingProducts(3);
//            return ResponseDTO.success(order);
//        } catch (Exception e) {
//            return ResponseDTO.error();
//        }
//    }
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DIRECTOR')")
    public ResponseDTO statisticsBill() {
        return ResponseDTO.success(this.orderService.getStatisticsBill());
    }
    @GetMapping("/order-bill")
    public ResponseDTO getOrderBill() {
        return ResponseDTO.success(this.orderService.getListBill());
    }

    @PostMapping("/transaction-success")
    public ResponseDTO updateTransaction(@RequestBody BillDTO dto){
        this.orderService.updatePaymentTransaction(dto);
        return ResponseDTO.success();
    }

}
