package com.example.demo.web.rest;

import com.example.demo.domain.Product;
import com.example.demo.service.OrderService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.order.BillDTO;
import com.example.demo.service.dto.order.ViewBillDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/nest/order")
@RestController
@CrossOrigin("*")
public class OrderResource {

    private final OrderService orderService;

<<<<<<< HEAD
    OrderResource(OrderService orderService){
=======
    OrderResource(OrderService orderService) {
>>>>>>> 392f61fde1bee39e403c7f3758cc5c1cf2cf47fb
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

    @GetMapping("/get-detail/{id}")
    public ResponseDTO getDetailBill(@PathVariable Long id) {
        return ResponseDTO.success(this.orderService.getBillDetail(id));
    }

    @PostMapping("/create-bill")
    public ResponseDTO createBill(@RequestBody ViewBillDetail cart) {
        this.orderService.createBill(cart);
        return ResponseDTO.success();
    }

    @GetMapping("/list/selling")
    public ResponseDTO getTop10ProductPopular(){
        try {
            List<Product> order = orderService.getTopSellingProducts(3);
            return ResponseDTO.success(order);
        } catch (Exception e) {
            return ResponseDTO.error();
        }
    }
}
