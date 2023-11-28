package com.example.demo.service;

import com.example.demo.domain.Bill;
import com.example.demo.domain.BillDetail;
import com.example.demo.domain.Category;
import com.example.demo.domain.Product;
import com.example.demo.repository.ICategotyRepository;
import com.example.demo.repository.IOrderDetailRepository;
import com.example.demo.repository.IOrderRepository;
import com.example.demo.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    @Autowired
    IProductRepository productReponsitory;
    @Autowired
    IOrderDetailRepository orderDetailReponsitory;
    @Autowired
    IOrderRepository orderReponsityory;
    @Autowired
    ICategotyRepository categoryRepository;

    public int getQtyProduct(){
        List<Product> product = productReponsitory.findAll();
        List<BillDetail> bill = orderDetailReponsitory.findAll();
        int QtyProduct = 0;
        int QtyBillDetail = 0;
        for (Product pro: product
             ) {
            QtyProduct += pro.getQuantity();
        }
        for (BillDetail billDetail: bill
             ) {
            QtyBillDetail += billDetail.getQuantity();
        }
        return QtyProduct-QtyBillDetail;
    }

    public int getQtyOder(){
        List<Bill> bill = orderReponsityory.findAll();
        int QtyBill = 0;
        for(Bill bills: bill){
            QtyBill += 1;
        }
        return QtyBill;
    }

    public int getRevenue() {
        List<BillDetail> billDetail = orderDetailReponsitory.findAll();
        BigDecimal priceRevenue = new BigDecimal(0);
        for (BillDetail billDetails : billDetail) {
            priceRevenue = priceRevenue.add(new BigDecimal(String.valueOf(billDetails.getPrice())));
        }
        return priceRevenue.intValue();
    }

    public List<Bill> getOrdersInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, 0);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();
        return orderReponsityory.findByOrderDateBetween(startDate, endDate);
    }
}
