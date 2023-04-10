package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.mapper.ProductMapper;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.repository.ProductRepository;
import com.example.bankapplication.service.ProductService;
import com.example.bankapplication.service.exception.ErrorMessage;
import com.example.bankapplication.service.exception.ManagerNotFoundException;
import com.example.bankapplication.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ManagerRepository managerRepository;

    @Override
    public ProductListDTO getAll() {
        log.info("Get all products");
        return new ProductListDTO(productMapper.productsToProductsDTO(productRepository.findAll()));
    }

    @Override
    public ProductDTO getProductById(UUID id) {
        log.info("Get a product with id {}", id);
        if(id == null){
            throw new NullPointerException("This id {" + id + "} is null");
        }
        return productMapper.toDTO(productRepository.findProductById(id).orElseThrow(
                () -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
        ));
    }

    @Override
    public ProductDTO create(CreateProductDTO dto) {
        log.info("Creating manager");
        var managerId = dto.getManagerId();
        log.info(dto.getManagerId().toString());
        var manager = managerRepository.findManagerById(managerId).orElseThrow(
                () -> new ManagerNotFoundException(ErrorMessage.Manager_NOT_FOUND)
        );

        var product = productMapper.createToEntity(dto);
        product.setManager(manager);
        var result = productRepository.save(product);
        return productMapper.toDTO(result);
    }
}
