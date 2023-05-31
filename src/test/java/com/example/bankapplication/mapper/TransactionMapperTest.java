package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.CreateTransactionDTO;
import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.entity.Transaction;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test class for TransactionMapper")
class TransactionMapperTest {
    private final TransactionMapper transactionMapper = new TransactionMapperImpl();

    private Transaction transaction;
    private List<Transaction> transactionList;
    private TransactionDTO transactionDTO;
    private List<TransactionDTO> transactionDTOList;
    private CreateTransactionDTO createTransactionDTO;

    @BeforeEach
    void setUp() {
        transaction = EntityCreator.getTransaction();
        transactionDTO = DTOCreator.getTransactionDTO();
        transactionList = new ArrayList<>(List.of(transaction));
        transactionDTOList = new ArrayList<>(List.of(transactionDTO));
        createTransactionDTO = DTOCreator.getTransactionToCreateWithCreateDate();
    }

    @Test
    @DisplayName("Positive test. When we have correct entity then return correct TransactionDto")
    void testToDTO() {
        TransactionDTO transactionDTO = transactionMapper.toDTO(transaction);
        compareEntityWithDto(transaction, transactionDTO);
    }

    @Test
    void testToDTONull() {
        TransactionDTO transactionDTO = transactionMapper.toDTO(null);
        assertNull(transactionDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct TransactionDto then return correct entity")
    void testToEntity() {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        compareEntityWithDto(transaction, transactionDTO);
    }

    @Test
    void testToEntityNull() {
        Transaction transaction = transactionMapper.toEntity(null);
        assertNull(transaction);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Transaction then return correct list of TransactionDto")
    void testTransactionsToTransactionsDTO() {
        List<TransactionDTO> transactionDTOList = transactionMapper.transactionsToTransactionsDTO(transactionList);
        compareManagerListWithListDto(transactionList, transactionDTOList);
    }

    @Test
    void testTransactionsToTransactionsDTONull() {
        List<TransactionDTO> transactionDTOList = transactionMapper.transactionsToTransactionsDTO(null);
        assertNull(transactionDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        Transaction transaction = transactionMapper.createToEntity(createTransactionDTO);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String transactionDate = date.format(transaction.getCreatedAt());

        assertNull(createTransactionDTO.getCreatedAt());
        assertNotNull(transaction.getCreatedAt());
        assertEquals(current, transactionDate);
    }

    @Test
    void testCreateToEntityNull() {
        Transaction transaction = transactionMapper.createToEntity(null);
        assertNull(transaction);
    }

    private void compareEntityWithDto(Transaction transaction, TransactionDTO transactionDTO) {
        assertAll(
                () -> assertEquals(transaction.getId().toString(), transactionDTO.getId()),
                () -> assertEquals(transaction.getType().toString(), transactionDTO.getType()),
                () -> assertEquals(Double.toString(transaction.getAmount()), transactionDTO.getAmount()),
                () -> assertEquals(transaction.getDescription(), transactionDTO.getDescription()),
                () -> assertEquals(transaction.getCreatedAt(), transactionDTO.getCreatedAt())
        );
    }

    private void compareManagerListWithListDto(List<Transaction> transactionList, List<TransactionDTO> transactionDTOList) {
        assertEquals(transactionList.size(), transactionDTOList.size());
        for (int i = 0; i < transactionList.size(); i++) {
            compareEntityWithDto(transactionList.get(i), transactionDTOList.get(i));
        }
    }
}