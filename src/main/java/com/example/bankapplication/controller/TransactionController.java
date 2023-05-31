package com.example.bankapplication.controller;

import com.example.bankapplication.dto.CreateTransactionDTO;
import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.dto.TransactionListDTO;
import com.example.bankapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The @RestController annotation indicates that this class is a controller that handles HTTP requests
 * and produces JSON or XML responses.
 * <p>
 * The @RequestMapping("/auth") annotation specifies the base URL path for all the endpoints in this controller,
 * which will be "/auth".
 * <p>
 * The @RequiredArgsConstructor annotation is a Lombok annotation that generates a constructor
 * with required arguments for the class fields marked with final.
 * <p>
 * The @PostMapping annotation is used to map HTTP POST requests to specific methods in a controller class.
 * It defines the URL path for the endpoint and specifies the logic to handle the POST request and generate the response.
 * <p>
 * The @GetMapping annotation is used to map HTTP GET requests to specific methods in a controller class.
 * It specifies the URL path for the endpoint and defines the logic to handle the GET request and generate the response.
 * <p>
 * The @DeleteMapping annotation is used to map HTTP DELETE requests to specific methods in a controller class.
 * It specifies the URL path for the endpoint and defines the logic to handle the DELETE request.
 * <p>
 * The @PutMapping annotation is used to map HTTP PUT requests to specific methods in a controller class.
 * It specifies the URL path for the endpoint and defines the logic to handle the PUT request.
 * <p>
 * The @ResponseStatus annotation is used to specify the default HTTP response status code for
 * a particular method or exception handler in a controller class.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("transactions/all")
    @ResponseStatus(HttpStatus.OK)
    public TransactionListDTO getAll() {
        return transactionService.getAll();
    }

    @GetMapping("transaction/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping("createTransaction")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO createTransaction(@RequestBody CreateTransactionDTO dto) {
        return transactionService.createTransaction(dto);
    }

    @DeleteMapping("deleteTransaction/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransactionById(@PathVariable UUID id) {
        transactionService.deleteTransactionById(id);
    }
}