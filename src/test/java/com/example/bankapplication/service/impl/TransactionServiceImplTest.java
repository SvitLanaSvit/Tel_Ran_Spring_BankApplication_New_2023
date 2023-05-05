package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateTransactionDTO;
import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.dto.TransactionListDTO;
import com.example.bankapplication.entity.Transaction;
import com.example.bankapplication.mapper.TransactionMapper;
import com.example.bankapplication.mapper.TransactionMapperImpl;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.TransactionRepository;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for TransactionServiceImpl")
class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    private TransactionMapper transactionMapper;

    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp(){
        transactionMapper = new TransactionMapperImpl();
        transactionService = new TransactionServiceImpl(transactionMapper, transactionRepository, accountRepository);
    }

    @Test
    void testGetAll() {
        UUID id = UUID.randomUUID();
        List<Transaction> transactionList = new ArrayList<>(List.of(EntityCreator.getTransaction()));
        List<TransactionDTO> transactionDTOList = new ArrayList<>(List.of(DTOCreator.getTransactionDTO()));
        TransactionListDTO expectedTransactionlistDTO = new TransactionListDTO(transactionDTOList);

        when(transactionRepository.findAll()).thenReturn(transactionList);
        TransactionListDTO actualTransactionlistDTO = transactionService.getAll();

        verify(transactionRepository).findAll();
        compareListDto(expectedTransactionlistDTO, actualTransactionlistDTO);
    }

    @Test
    void testGetTransactionById(){
        Transaction transaction = EntityCreator.getTransaction();
        TransactionDTO expectedTransactionDTO = DTOCreator.getTransactionDTO();

        when(transactionRepository.findTransactionById(any(UUID.class)))
                .thenReturn(Optional.of(transaction));
        TransactionDTO actualTransactionDTO = transactionService.getTransactionById(UUID.randomUUID());
        verify(transactionRepository, times(1)).findTransactionById(any(UUID.class));
        compareEntityWithDto(expectedTransactionDTO, actualTransactionDTO);
    }

    @Test
    void testCreateTransaction(){
        UUID transactionId = UUID.fromString("72779690-8d70-43cf-97a8-d3e7b9076337");
        CreateTransactionDTO createTransactionDTO = DTOCreator.getTransactionToCreate();
        Transaction expectedTransaction = EntityCreator.getTransactionAfterDTO(transactionId, createTransactionDTO);
        TransactionDTO expectedTransactionDTO = DTOCreator.getTransactionDTO();

        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);
        when(accountRepository.findAccountById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getAccount(UUID.randomUUID())));

        TransactionDTO actualTransactionDTO = transactionService.createTransaction(createTransactionDTO);
        assertNotNull(actualTransactionDTO);
        compareEntityWithDto(expectedTransactionDTO, actualTransactionDTO);
    }

    @Test
    void testDeleteTransactionById(){
        UUID transactionId = UUID.randomUUID();
        when(transactionRepository.findTransactionById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getTransaction()));

        transactionService.deleteTransactionById(transactionId);
        verify(transactionRepository, times(1)).findTransactionById(any(UUID.class));
        verify(transactionRepository, times(1)).deleteById(any(UUID.class));
    }

    private void compareListDto(TransactionListDTO expectedTransactionListDTO, TransactionListDTO actualTransactionListDTO){
        for(int i = 0; i < expectedTransactionListDTO.getTransactionDTOList().size(); i++){
            compareEntityWithDto(expectedTransactionListDTO.getTransactionDTOList().get(i),
                    actualTransactionListDTO.getTransactionDTOList().get(i));
        }
    }

    private void compareEntityWithDto(TransactionDTO expectedTransactionDTO, TransactionDTO actualTransactionDTO){
        assertAll(
                () -> assertEquals(expectedTransactionDTO.getId(), actualTransactionDTO.getId()),
                () -> assertEquals(expectedTransactionDTO.getType(), actualTransactionDTO.getType()),
                () -> assertEquals(expectedTransactionDTO.getAmount(), actualTransactionDTO.getAmount()),
                () -> assertEquals(expectedTransactionDTO.getDescription(), actualTransactionDTO.getDescription()),
                () -> assertEquals(expectedTransactionDTO.getDebitAccountId(), actualTransactionDTO.getDebitAccountId()),
                () -> assertEquals(expectedTransactionDTO.getCreditAccountId(), actualTransactionDTO.getCreditAccountId())
        );
    }
}