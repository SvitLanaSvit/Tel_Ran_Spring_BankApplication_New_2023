package com.example.bankapplication.service;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.dto.ProductListDTO;

import java.util.UUID;

public interface ProductService {
    ProductListDTO getAll();
    ProductDTO getProductById(UUID id);
    ProductDTO create(CreateProductDTO dto);
}
