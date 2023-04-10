package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.entity.enums.CurrencyCode;
import com.example.bankapplication.entity.enums.ProductStatus;
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

import java.sql.Timestamp;
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

    @Override
    public ProductDTO editProductById(UUID id, CreateProductDTO dto) {
        log.info("Edit product {}", id);

        var product = productRepository.findProductById(id).orElseThrow(
                () -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
        );
        var managerId = dto.getManagerId();
        var manager = managerRepository.findManagerById(managerId).orElseThrow(
                () -> new ManagerNotFoundException(ErrorMessage.Manager_NOT_FOUND)
        );

        product.setName(dto.getName());
        product.setStatus(ProductStatus.valueOf(dto.getStatus()));
        product.setCurrencyCode(CurrencyCode.valueOf(dto.getCurrencyCode()));
        product.setInterestRate(Double.parseDouble(dto.getInterestRate()));
        product.setProductLimit(Integer.parseInt(dto.getProductLimit()));
        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        product.setManager(manager);

        var result = productRepository.save(product);
        return productMapper.toDTO(result);
    }

    @Override
    public void deleteProductById(UUID id) {
        log.info("Deleting product {}", id);
        var product = productRepository.findProductById(id);
        if(product.isPresent())
            productRepository.deleteById(id);
        else{
            throw new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        }
    }
}
