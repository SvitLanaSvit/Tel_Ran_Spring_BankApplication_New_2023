package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.CreateTransactionDTO;
import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

/**
 * The `TransactionMapper` interface is a mapper interface using the MapStruct library. It provides mapping methods
 * to convert between `Transaction` entities and `TransactionDTO` data transfer objects.
 *
 * `@Mapper(componentModel = "spring", imports = Timestamp.class)`: This annotation is from the MapStruct library
 * and specifies that this interface should be treated as a mapper component.
 * The `componentModel="spring"` attribute indicates that Spring should manage the lifecycle of the mapper bean.
 * The `imports = Timestamp.class` attribute specifies that the `Timestamp` class should be imported for usage
 * in mapping expressions.
 *
 * `@Mapping(source = "transaction.debitAccount.id", target = "debitAccountId")`:
 * This annotation is used on the `toDTO` method to specify a mapping between the `debitAccount.id` property
 * of the `Transaction` entity and the `debitAccountId` property of the `TransactionDTO`. It maps the value from
 * `transaction.debitAccount.id` to `debitAccountId` during the conversion.
 *
 * `@Mapping(source = "transaction.creditAccount.id", target = "creditAccountId")`:
 * This annotation is used on the `toDTO` method to specify a mapping between the `creditAccount.id` property
 * of the `Transaction` entity and the `creditAccountId` property of the `TransactionDTO`. It maps the value from
 * `transaction.creditAccount.id` to `creditAccountId` during the conversion.
 *
 * `TransactionDTO toDTO(Transaction transaction)`:
 * This method maps a `Transaction` entity to a `TransactionDTO`.
 *
 * `Transaction toEntity(TransactionDTO transactionDTO)`:
 * This method maps a `TransactionDTO` to a `Transaction` entity.
 *
 * `List<TransactionDTO> transactionsToTransactionsDTO(List<Transaction> transactions)`:
 * This method maps a list of `Transaction` entities to a list of `TransactionDTO` objects.
 *
 * `@Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")`:
 * This annotation is used on the `createToEntity` method to set the `createdAt` property of the `Transaction` entity.
 * It uses a mapping expression to create a new `Timestamp` object representing the current system time.
 *
 * `Transaction createToEntity(CreateTransactionDTO dto)`:
 * This method maps a `CreateTransactionDTO` to a `Transaction` entity, including setting the `createdAt` property using
 * the mapping expression defined by the `@Mapping` annotation.
 */
@Mapper(componentModel = "spring", /*uses = UuidMapper.class,*/ imports = Timestamp.class)
public interface TransactionMapper {
    @Mapping(source = "transaction.debitAccount.id", target = "debitAccountId")
    @Mapping(source = "transaction.creditAccount.id", target = "creditAccountId")
    TransactionDTO toDTO(Transaction transaction);
    Transaction toEntity(TransactionDTO transactionDTO);
    List<TransactionDTO> transactionsToTransactionsDTO(List<Transaction> transactions);
    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Transaction createToEntity(CreateTransactionDTO dto);
}