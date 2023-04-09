package com.example.bankapplication.controller;

import com.example.bankapplication.dto.TransactionListDTO;
import com.example.bankapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
