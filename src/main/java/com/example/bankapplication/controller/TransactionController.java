package com.example.bankapplication.controller;

import com.example.bankapplication.dto.CreateTransactionDTO;
import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.dto.TransactionListDTO;
import com.example.bankapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("transactions/all")
    @ResponseStatus(HttpStatus.OK)
    public TransactionListDTO getAll(){
        return transactionService.getAll();
    }

    @GetMapping("transaction/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO getTransactionById(@PathVariable UUID id){
        return transactionService.getTransactionById(id);
    }

    @PostMapping("createTransaction")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO createTransaction(@RequestBody CreateTransactionDTO dto){
        return transactionService.createTransaction(dto);
    }

    @DeleteMapping("deleteTransaction/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransactionById(@PathVariable UUID id){
        transactionService.deleteTransactionById(id);
    }
}