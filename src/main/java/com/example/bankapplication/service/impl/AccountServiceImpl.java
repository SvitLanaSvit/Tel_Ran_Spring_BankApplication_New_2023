package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.AccountIdDTO;
import com.example.bankapplication.dto.AccountListDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.entity.enums.AccountType;
import com.example.bankapplication.entity.enums.CurrencyCode;
import com.example.bankapplication.entity.enums.ProductStatus;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.service.AccountService;
import com.example.bankapplication.service.exception.AccountNotFoundException;
import com.example.bankapplication.service.exception.ClientNotFoundException;
import com.example.bankapplication.service.exception.ErrorMessage;
import com.example.bankapplication.service.exception.NegativeDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * The `AccountServiceImpl` class is an implementation of the `AccountService` interface.
 * It provides methods for performing various operations related to accounts.
 *
 * @Service: This annotation is used to indicate that this class is a service component in the Spring framework.
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to inject dependencies using constructor injection.
 * @Slf4j: This annotation is from the Lombok library and generates a logger field for logging.
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
 * <p>
 * AccountRepository accountRepository: This field is used to access the account data in the database.
 * <p>
 * AccountMapper accountMapper: This field is used to map account entities to DTOs and vice versa.
 * <p>
 * ClientRepository clientRepository: This field is used to access client data in the database.
 * <p>
 * getAccountById(UUID id): This method retrieves an account by its unique identifier (`id`).
 * It throws an `AccountNotFoundException` if no account with the specified `id` is found.
 * <p>
 * getAllAccountsStatus(): This method retrieves all active accounts.
 * <p>
 * createAccount(CreateAccountDTO dto): This method creates a new account based on the provided DTO.
 * It throws a `ClientNotFoundException` if the client specified in the DTO is not found.
 * <p>
 * deleteAccountById(UUID id): This method deletes an account by its unique identifier (`id`).
 * It throws an `AccountNotFoundException` if no account with the specified `id` is found.
 * <p>
 * editAccountById(UUID id, CreateAccountDTO dto): This method updates an account with the specified `id`
 * using the information provided in the DTO. It throws an `AccountNotFoundException` if no account with the specified `id` is found,
 * and a `ClientNotFoundException` if the client specified in the DTO is not found.
 * <p>
 * getAll(): This method retrieves all accounts.
 * <p>
 * findAccountsByProductIdAndStatus(UUID id, ProductStatus status):
 * The method retrieves a list of AccountIdDTO objects.
 * It then invokes the findAccountByProductIdByStatus method of the accountRepository object,
 * passing in the id and status as arguments.
 * The returned result from findAccountByProductIdByStatus is returned by the findAccountsByProductIdAndStatus method.
 * <p>
 * The `AccountServiceImpl` class implements the `AccountService` interface,
 * which defines the contract for performing operations on accounts.
 * By implementing this interface, the class provides the necessary business logic for account-related operations.
 * <p>
 * With the `AccountServiceImpl` class, we can retrieve, create, update, and delete accounts,
 * as well as get all accounts and all active accounts.
 * It uses the `AccountRepository` and `ClientRepository` interfaces for data access,
 * and the `AccountMapper` interface for entity-DTO mapping.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public AccountDTO getAccountById(UUID id) {
        log.info("Get an account with id {}", id);
        return accountMapper.toDTO(accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND)
        ));
    }

    @Override
    @Transactional
    public AccountListDTO getAllAccountsStatus() {
        log.info("Get all active accounts");
        return new AccountListDTO(accountMapper.accountsToAccountsDTO(accountRepository.getAllByStatus(AccountStatus.ACTIVE)));
    }

    @Override
    @Transactional
    public AccountDTO createAccount(CreateAccountDTO dto) {
        log.info("Creating ");
        log.info("UUID : " + dto.getClientId());
        if (dto.getClientId() == null) {
            throw new NullPointerException("clientId cannot be null");
        }

        if (Double.parseDouble(dto.getBalance()) < 0.0) {
            throw new NegativeDataException(ErrorMessage.NEGATIVE_DATA);
        }

        var uuidClient = dto.getClientId();
        var client = clientRepository.findClientById(uuidClient).orElseThrow(
                () -> new ClientNotFoundException(ErrorMessage.CLIENT_NOT_FOUND)
        );
        var account = accountMapper.createToEntity(dto);
        account.setClient(client);
        var result = accountRepository.save(account);
        return accountMapper.toDTO(result);
    }

    @Override
    @Transactional
    public void deleteAccountById(UUID id) {
        log.info("Deleting account {}", id);
        var account = accountRepository.findAccountById(id);
        if (account.isPresent())
            accountRepository.deleteById(id);
        else throw new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND);
    }

    @Override
    @Transactional
    public AccountDTO editAccountById(UUID id, CreateAccountDTO dto) {
        var account = accountRepository.findAccountById(id).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND)
        );

        account.setName(dto.getName());
        account.setType(AccountType.valueOf(dto.getType()));
        account.setStatus(AccountStatus.valueOf(dto.getStatus()));
        account.setBalance(Double.parseDouble(dto.getBalance()));
        account.setCurrencyCode(CurrencyCode.valueOf(dto.getCurrencyCode()));
        account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        account.setClient(account.getClient());

        var result = accountRepository.save(account);
        return accountMapper.toDTO(result);
    }

    @Override
    public AccountListDTO getAll() {
        log.info("Get all accounts");
        return new AccountListDTO(accountMapper.accountsToAccountsDTO(accountRepository.findAll()));
    }

    @Override
    public List<AccountIdDTO> findAccountsByProductIdAndStatus(UUID id, ProductStatus status) {
        log.info("Get list of account id by product id {} and product status {}", id, status);
        List<AccountIdDTO> accountIdDTOList = accountRepository.findAccountByProductIdByStatus(id, status);
        if (accountIdDTOList.isEmpty()) {
            throw new NullPointerException("The list of Id from account find by product id and status is EMPTY!");
        }
        return accountIdDTOList;
    }
}