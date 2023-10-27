package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    IProductRepository productReponse;
    public Product findById(Long id) {
        return productReponse.findById(id).get();
    }
}
