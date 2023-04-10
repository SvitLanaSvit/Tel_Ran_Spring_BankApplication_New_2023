package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateTransactionDTO;
import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.dto.TransactionListDTO;
import com.example.bankapplication.mapper.TransactionMapper;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.TransactionRepository;
import com.example.bankapplication.service.TransactionService;
import com.example.bankapplication.service.exception.AccountNotFoundException;
import com.example.bankapplication.service.exception.ErrorMessage;
import com.example.bankapplication.service.exception.TransactionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public TransactionListDTO getAll() {
        log.info("Get all transactions");
        return new TransactionListDTO(
                transactionMapper.transactionsToTransactionsDTO(transactionRepository.findAll()));
    }

    @Override
    public TransactionDTO getTransactionById(UUID id) {
        log.info("Get a transaction with id {}", id);
        return transactionMapper.toDTO(transactionRepository.findTransactionById(id).orElseThrow(
                () -> new TransactionNotFoundException(ErrorMessage.TRANSACTION_NOT_FOUND)
        ));
    }

    @Override
    public TransactionDTO createTransaction(CreateTransactionDTO dto) {
        log.info("Creating transaction");

        var debitAccountId = dto.getDebitAccountId();
        var debitAccount = accountRepository.findAccountById(debitAccountId).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND)
        );

        var creditAccountId = dto.getCreditAccountId();
        var creditAccount = accountRepository.findAccountById(creditAccountId).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND)
        );

        var transaction = transactionMapper.createToEntity(dto);
        transaction.setCreditAccount(creditAccount);
        transaction.setDebitAccount(debitAccount);

        var result = transactionRepository.save(transaction);
        return transactionMapper.toDTO(result);
    }

    @Override
    public void deleteTransactionById(UUID id) {
        log.info("Deleting transaction {}", id);
        var transaction = transactionRepository.findTransactionById(id);
        if(transaction.isPresent())
            transactionRepository.deleteById(id);
        else throw new TransactionNotFoundException(ErrorMessage.TRANSACTION_NOT_FOUND);
    }
}
