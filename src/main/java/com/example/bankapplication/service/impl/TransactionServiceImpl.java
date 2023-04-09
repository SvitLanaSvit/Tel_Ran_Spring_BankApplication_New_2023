package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.TransactionListDTO;
import com.example.bankapplication.mapper.TransactionMapper;
import com.example.bankapplication.repository.TransactionRepository;
import com.example.bankapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    @Override
    public TransactionListDTO getAll() {
        log.info("Get all transactions");
        return new TransactionListDTO(
                transactionMapper.transactionsToTransactionsDTO(transactionRepository.findAll()));
    }
}
