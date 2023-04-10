package com.example.bankapplication.service;

import com.example.bankapplication.dto.CreateTransactionDTO;
import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.dto.TransactionListDTO;

import java.util.UUID;

public interface TransactionService {
    TransactionListDTO getAll();
    TransactionDTO getTransactionById(UUID id);
    TransactionDTO createTransaction(CreateTransactionDTO dto);
    void deleteTransactionById(UUID id);
}
