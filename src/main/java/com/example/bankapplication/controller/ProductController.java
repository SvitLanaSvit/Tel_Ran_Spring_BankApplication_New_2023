package com.example.bankapplication.controller;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @RequestMapping("products/all")
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAll(){
        return productService.getAll();
    }

    @RequestMapping("product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable UUID id){
        return productService.getProductById(id);
    }

    @PostMapping("createProduct")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO createProduct(@RequestBody CreateProductDTO dto){
        return productService.create(dto);
    }


}
