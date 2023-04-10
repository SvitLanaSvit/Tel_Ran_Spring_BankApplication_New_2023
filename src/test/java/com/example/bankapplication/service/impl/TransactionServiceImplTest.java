package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.dto.TransactionListDTO;
import com.example.bankapplication.entity.Transaction;
import com.example.bankapplication.mapper.TransactionMapper;
import com.example.bankapplication.repository.TransactionRepository;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionServiceImpl service;

    @Test
    void getAll() {
        UUID id = UUID.randomUUID();
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(EntityCreator.getTransaction(id));

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(DTOCreator.getTransactionDTO(id));

        TransactionListDTO listDTO = new TransactionListDTO(transactionDTOList);

        when(transactionRepository.findAll()).thenReturn(transactionList);
        when(transactionMapper.transactionsToTransactionsDTO(transactionList))
                .thenReturn(transactionDTOList);

        TransactionListDTO result = service.getAll();

        verify(transactionRepository).findAll();
        verify(transactionMapper).transactionsToTransactionsDTO(transactionList);
        assertEquals(listDTO.getTransactionDTOList().get(0).getId(), result.getTransactionDTOList().get(0).getId());
        assertEquals(listDTO.getTransactionDTOList().get(0).getCreatedAt(), result.getTransactionDTOList().get(0).getCreatedAt());
    }
}