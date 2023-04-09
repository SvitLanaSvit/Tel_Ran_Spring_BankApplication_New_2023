package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.mapper.ProductMapper;
import com.example.bankapplication.repository.ProductRepository;
import com.example.bankapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public ProductListDTO getAll() {
        log.info("Get all products");
        return new ProductListDTO(productMapper.productsToProductsDTO(productRepository.findAll()));
    }
}
