package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
