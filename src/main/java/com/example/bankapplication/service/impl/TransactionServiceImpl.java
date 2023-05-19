package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateTransactionDTO;
import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.dto.TransactionListDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.mapper.TransactionMapper;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.TransactionRepository;
import com.example.bankapplication.service.TransactionService;
import com.example.bankapplication.service.exception.AccountNotFoundException;
import com.example.bankapplication.service.exception.ErrorMessage;
import com.example.bankapplication.service.exception.TransactionNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The `TransactionServiceImpl` class is an implementation of the `TransactionService` interface.
 * It provides methods for performing various operations related to transactions.
 *
 * @Service: This annotation is used to indicate that this class is a service component in the Spring framework.
 *
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to inject dependencies using constructor injection.
 *
 * @Slf4j: This annotation is from the Lombok library and generates a logger field for logging.
 *
 * @Transactional: This annotation is used in Spring to define transactional boundaries for methods or classes.
 * When applied to a method or class, it indicates that a transaction should be created for the annotated method
 * or all methods within the annotated class.
 * Transactional boundaries ensure that a group of operations are executed as a single atomic unit.
 * If an exception occurs during the execution of the annotated method or any method within the annotated class,
 * the transaction will be rolled back, and any changes made within the transaction will be undone.
 * By using the `@Transactional` annotation, we can manage transactions declaratively without having
 * to write explicit transaction management code. Spring takes care of creating, committing,
 * or rolling back transactions based on the annotated method's execution.
 * It is important to note that the `@Transactional` annotation should be applied to methods that modify data
 * or perform multiple database operations to ensure data integrity and consistency.
 *
 * TransactionMapper transactionMapper: This field is used to map transaction entities to DTOs and vice versa.
 *
 * TransactionRepository transactionRepository: This field is used to access and manipulate transaction data in the database.
 *
 * AccountRepository accountRepository: This field is used to access and manipulate account data in the database.
 *
 * getAll(): This method retrieves all transactions from the database and returns them as a `TransactionListDTO` object.
 *
 * getTransactionById(UUID id): This method retrieves a transaction by its ID from the database and returns it as a `TransactionDTO` object.
 * If the transaction is not found, it throws a `TransactionNotFoundException`.
 *
 * createTransaction(CreateTransactionDTO dto): This method creates a new transaction based on the provided information.
 * It retrieves the debit and credit accounts from the database using the account repository,
 * maps the DTO to an entity, sets the accounts in the transaction, saves the transaction to the database, and returns the created transaction as a `TransactionDTO` object.
 *
 * deleteTransactionById(UUID id): This method deletes a transaction by its ID from the database.
 * If the transaction is not found, it throws a `TransactionNotFoundException`.
 *
 * getAccount(UUID id): This is a helper method that retrieves an account by its ID from the database using the account repository.
 * If the account is not found, it throws an `AccountNotFoundException`.
 *
 * The `TransactionServiceImpl` class implements the `TransactionService` interface,
 * which defines the contract for performing operations on transactions.
 * By implementing this interface, the class provides the necessary business logic for transaction-related operations.
 *
 * With the `TransactionServiceImpl` class, we can retrieve all transactions, retrieve a transaction by ID,
 * create a new transaction, and delete a transaction by ID.
 * It uses the transaction repository to access and manipulate transaction data,
 * and the account repository to retrieve account information when creating a transaction.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public TransactionListDTO getAll() {
        log.info("Get all transactions");
        return new TransactionListDTO(
                transactionMapper.transactionsToTransactionsDTO(transactionRepository.findAll()));
    }

    @Override
    @Transactional
    public TransactionDTO getTransactionById(UUID id) {
        log.info("Get a transaction with id {}", id);
        return transactionMapper.toDTO(transactionRepository.findTransactionById(id).orElseThrow(
                () -> new TransactionNotFoundException(ErrorMessage.TRANSACTION_NOT_FOUND)
        ));
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(CreateTransactionDTO dto) {
        log.info("Creating transaction");

        var debitAccount = getAccount(dto.getDebitAccountId());

        var creditAccount = getAccount(dto.getDebitAccountId());

        var transaction = transactionMapper.createToEntity(dto);
        transaction.setCreditAccount(creditAccount);
        transaction.setDebitAccount(debitAccount);

        var result = transactionRepository.save(transaction);
        return transactionMapper.toDTO(result);
    }

    private Account getAccount(UUID id){
        return accountRepository.findAccountById(id).orElseThrow(
                ()-> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public void deleteTransactionById(UUID id) {
        log.info("Deleting transaction {}", id);
        var transaction = transactionRepository.findTransactionById(id);
        if(transaction.isPresent())
            transactionRepository.deleteById(id);
        else throw new TransactionNotFoundException(ErrorMessage.TRANSACTION_NOT_FOUND);
    }
}