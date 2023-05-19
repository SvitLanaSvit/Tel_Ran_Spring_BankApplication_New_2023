package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

/**
 * The `AccountMapper` interface a mapper interface using the MapStruct library. It provides mapping methods
 * to convert between `Account` entities and `AccountDTO` data transfer objects.
 *
 * `@Mapper(componentModel = "spring", imports=Timestamp.class)`:This annotation is from the MapStruct library
 * and specifies that this interface should be treated as a mapper component.
 * The `componentModel="spring"` attribute indicates that Spring should manage the lifecycle of the mapper bean.
 * The `imports=Timestamp.class` attribute specifies that the `Timestamp` class should be imported for usage
 * in mapping expressions.
 *
 * `@Mapping(source = "account.client.id", target = "clientId")`:
 * This annotation is used on the `toDTO` method to specify a mapping between the `clientId` property
 * of the `Account` entity and the `clientId` property of the `AccountDTO`.It maps the value from `account.client.id`
 * to `clientId` during the conversion.
 *
 * `AccountDTO toDTO(Account account)`:
 * This method maps an `Account` entity to an `AccountDTO`.
 * It uses the `@Mapping` annotation to define the mapping between properties.
 *
 * `Account toEntity(AccountDTO accountDTO)`:This method maps an `AccountDTO` to an `Account` entity.
 *
 * `List<AccountDTO> accountsToAccountsDTO(List<Account> accounts)`:
 * This method maps a list of `Account` entities to a list of `AccountDTO` objects.
 *
 * `@Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")`:
 * This annotation is used on the `createToEntity` method to set the `createdAt` property of the `Account` entity.
 * It uses a mapping expression to create a new `Timestamp` object representing the current system time.
 *
 * `Account createToEntity(CreateAccountDTO dto)`:
 * This method maps a `CreateAccountDTO` to an `Account` entity,including setting the `createdAt` property using
 * the mapping expression defined by the `@Mapping` annotation.
 */
@Mapper(componentModel = "spring", /*uses = UuidMapper.class,*/ imports = Timestamp.class)
public interface AccountMapper {
    /*
     Annotation '@Mapping' write if we need only clientId, otherwise we remove it and in AccountDTO instead of 'String clientId'
     write ClientDTO client
    */
    @Mapping(source = "account.client.id", target = "clientId")
    AccountDTO toDTO(Account account);
    Account toEntity(AccountDTO accountDTO);
    List<AccountDTO> accountsToAccountsDTO(List<Account> accounts);
    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Account createToEntity(CreateAccountDTO dto);
}