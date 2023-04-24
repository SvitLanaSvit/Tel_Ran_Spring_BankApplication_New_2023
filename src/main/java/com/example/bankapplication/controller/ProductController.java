package com.example.bankapplication.controller;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.service.ProductService;
import com.example.bankapplication.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final RequestService requestService;

    @GetMapping("products/all")
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAll(){
        return productService.getAll();
    }

    @GetMapping("product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable UUID id){
        return productService.getProductById(id);
    }

    @PostMapping("createProduct")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO createProduct(@RequestBody CreateProductDTO dto){
        return productService.create(dto);
    }

    @PutMapping("editProduct/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO editProductById(@PathVariable UUID id, @RequestBody CreateProductDTO dto){
        return productService.editProductById(id, dto);
    }

    @DeleteMapping("deleteProduct/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable UUID id){
        productService.deleteProductById(id);
    }

    @GetMapping("changedProducts")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ProductDTO> getAllChangedProducts(){
        return requestService.findAllChangedProducts();
    }
}
