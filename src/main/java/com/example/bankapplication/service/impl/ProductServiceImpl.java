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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * The `ProductServiceImpl` class is an implementation of the `ProductService` interface.
 * It provides methods for performing various operations related to products.
 *
 * @Service: This annotation is used to indicate that this class is a service component in the Spring framework.
 *
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to inject dependencies using constructor injection.
 *
 * @Slf4j: This annotation is from the Lombok library and generates a logger field for logging.
 *
 * @Transactional: This annotation is used in Spring to define transactional boundaries for methods or classes.
 * When applied to a method or class, it indicates that a transaction should be created for the annotated method
 * or all methods within the annotated class.
 * Transactional boundaries ensure that a group of operations are executed as a single atomic unit.
 * If an exception occurs during the execution of the annotated method or any method within the annotated class,
 * the transaction will be rolled back, and any changes made within the transaction will be undone.
 * By using the `@Transactional` annotation, we can manage transactions declaratively without having
 * to write explicit transaction management code. Spring takes care of creating, committing,
 * or rolling back transactions based on the annotated method's execution.
 * It is important to note that the `@Transactional` annotation should be applied to methods that modify data
 * or perform multiple database operations to ensure data integrity and consistency.
 *
 * ProductMapper productMapper: This field is used to map product entities to DTOs and vice versa.
 *
 * ProductRepository productRepository: This field is used to access the product data in the database.
 *
 * ManagerRepository managerRepository: This field is used to access the manager data in the database.
 *
 * getAll(): This method retrieves all products.
 *
 * getProductById(UUID id): This method retrieves a product by its unique identifier (`id`).
 * It throws a `ProductNotFoundException` if no product with the specified `id` is found.
 *
 * create(CreateProductDTO dto): This method creates a new product based on the provided DTO.
 * It also associates the product with a manager specified by the `managerId` in the DTO.
 * It throws a `ManagerNotFoundException` if no manager with the specified `managerId` is found.
 *
 * editProductById(UUID id, CreateProductDTO dto): This method updates a product with the specified `id`
 * using the information provided in the DTO. It throws a `ProductNotFoundException` if no product with the specified `id` is found.
 * It also updates the associated manager based on the `managerId` in the DTO.
 * It throws a `ManagerNotFoundException` if no manager with the specified `managerId` is found.
 *
 * deleteProductById(UUID id): This method deletes a product by its unique identifier (`id`).
 * It throws a `ProductNotFoundException` if no product with the specified `id` is found.
 *
 * The `ProductServiceImpl` class implements the `ProductService` interface,
 * which defines the contract for performing operations on products.
 * By implementing this interface, the class provides the necessary business logic for product-related operations.
 *
 * With the `ProductServiceImpl` class, we can retrieve, create, update, and delete products,
 * as well as get all products. It uses the `ProductRepository` interface for data access,
 * the `ProductMapper` interface for entity-DTO mapping, and the `ManagerRepository` interface to access manager data.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ManagerRepository managerRepository;

    @Override
    @Transactional
    public ProductListDTO getAll() {
        log.info("Get all products");
        return new ProductListDTO(productMapper.productsToProductsDTO(productRepository.findAll()));
    }

    @Override
    @Transactional
    public ProductDTO getProductById(UUID id) {
        log.info("Get a product with id {}", id);
        return productMapper.toDTO(productRepository.findProductById(id).orElseThrow(
                () -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
        ));
    }

    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
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