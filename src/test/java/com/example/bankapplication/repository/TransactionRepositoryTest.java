package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Transaction;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for TransactionRepository")
class TransactionRepositoryTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("Positive test. Find all transactions.")
    void testFindAll() {
        List<Transaction> transactions = new ArrayList<>(List.of(
                EntityCreator.getTransaction()
        ));

        when(transactionRepository.findAll()).thenReturn(transactions);
        List<Transaction> foundTransaction = transactionRepository.findAll();

        assertEquals(transactions.size(), foundTransaction.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Positive test. Find transaction by id.")
    void findTransactionById() {
        Transaction transaction = EntityCreator.getTransaction();

        when(transactionRepository.findTransactionById(transaction.getId())).thenReturn(Optional.of(transaction));
        Optional<Transaction> foundTransaction = transactionRepository.findTransactionById(transaction.getId());

        assertTrue(foundTransaction.isPresent());
        assertEquals(transaction, foundTransaction.get());
        verify(transactionRepository, times(1)).findTransactionById(transaction.getId());
    }
}