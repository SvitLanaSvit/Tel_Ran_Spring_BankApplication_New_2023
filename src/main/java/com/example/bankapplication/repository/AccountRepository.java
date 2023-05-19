package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The `AccountRepository` interface extends the `JpaRepository` interface, which is provided by the Spring Data JPA framework.
 * This interface defines methods for performing CRUD (Create, Read, Update, Delete) operations on the `Account` entity.
 *
 * 1. `Optional<Account> findAccountById(UUID id)`:
 * This method retrieves an `Account` entity by its unique identifier (`id`).
 * It returns an `Optional` that may contain the found `Account` or be empty if no `Account` with the specified `id` exists.
 *
 * 2. `List<Account> getAllByStatus(AccountStatus status)`:
 * This method retrieves a list of `Account` entities that match the specified `status`.
 * It returns all `Account` entities that have the given `status`.
 *
 * 3. `List<Account> findAll()`: This method retrieves all `Account` entities from the repository.
 * It returns a list of all `Account` entities in the database.
 *
 * The `JpaRepository` interface provides additional methods for common data access operations,
 * such as saving entities, deleting entities, and performing pagination and sorting.
 *
 * By extending the `JpaRepository` interface and specifying the `Account` entity class
 * and the type of its primary key (`UUID`), the `AccountRepository` interface inherits these methods
 * and also allows you to define custom repository methods specific to the `Account` entity.
 *
 * With the `AccountRepository` interface, we can easily perform database operations on the `Account` entity,
 * such as finding accounts by ID, retrieving accounts by status, and accessing all accounts in the repository.
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findAccountById(UUID id);
    List<Account> getAllByStatus(AccountStatus status);
    List<Account> findAll();
}