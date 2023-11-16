package com.example.demo.web.rest;

import com.example.demo.domain.Bill;
import com.example.demo.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/nest/report")
@AllArgsConstructor
@CrossOrigin("*")
public class ReportResource {
    @Autowired
    ReportService reportService;

    @GetMapping("/getProduct")
    public int getAllQtyProduct(){
        return reportService.getQtyProduct();
    }

    @GetMapping("/getOrder")
    public int getQtyOrder(){
        return reportService.getQtyOder();
    }

    @GetMapping("/getRevenue")
    public int getRevenue(){
        return reportService.getRevenue();
    }

    @GetMapping("/monthly")
    public int getMonthlyOrders() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng trong lịch bắt đầu từ 0

        List<Bill> monthlyOrders = reportService.getOrdersInMonth(year, month);
        int qtyOrder = 0;
        for(Bill bill : monthlyOrders){
            qtyOrder ++;
        }
        return qtyOrder;
    }
}
