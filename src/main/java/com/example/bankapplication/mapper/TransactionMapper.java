package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

@Mapper(componentModel = "spring", uses = UuidMapper.class, imports = Timestamp.class)
public interface TransactionMapper {
    @Mapping(source = "transaction.debitAccount.id", target = "debitAccountId")
    @Mapping(source = "transaction.creditAccount.id", target = "creditAccountId")
    TransactionDTO toDTO(Transaction transaction);
    Transaction toEntity(TransactionDTO transactionDTO);
    List<TransactionDTO> transactionsToTransactionsDTO(List<Transaction> transactions);
}
