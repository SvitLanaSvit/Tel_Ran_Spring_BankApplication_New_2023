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

/**
 * The @RestController annotation indicates that this class is a controller that handles HTTP requests
 * and produces JSON or XML responses.
 *
 * The @RequestMapping("/auth") annotation specifies the base URL path for all the endpoints in this controller,
 * which will be "/auth".
 *
 * The @RequiredArgsConstructor annotation is a Lombok annotation that generates a constructor
 * with required arguments for the class fields marked with final.
 *
 * The @PostMapping annotation is used to map HTTP POST requests to specific methods in a controller class.
 * It defines the URL path for the endpoint and specifies the logic to handle the POST request and generate the response.
 *
 *The @GetMapping annotation is used to map HTTP GET requests to specific methods in a controller class.
 * It specifies the URL path for the endpoint and defines the logic to handle the GET request and generate the response.
 *
 *The @DeleteMapping annotation is used to map HTTP DELETE requests to specific methods in a controller class.
 * It specifies the URL path for the endpoint and defines the logic to handle the DELETE request.
 *
 *The @PutMapping annotation is used to map HTTP PUT requests to specific methods in a controller class.
 * It specifies the URL path for the endpoint and defines the logic to handle the PUT request.
 *
 * The @ResponseStatus annotation is used to specify the default HTTP response status code for
 * a particular method or exception handler in a controller class.
 */
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
