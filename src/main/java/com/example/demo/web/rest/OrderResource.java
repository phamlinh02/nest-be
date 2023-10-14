package com.example.demo.web.rest;

import com.example.demo.service.OrderService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.order.BillDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDTO updateBill(@RequestBody BillDTO dto){
        return ResponseDTO.success(this.orderService.updateStatusBill(dto));
    }
}
