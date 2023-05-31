package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The `TransactionRepository` interface extends the `JpaRepository` interface, which is provided by the Spring Data JPA framework.
 * This interface defines methods for performing CRUD (Create, Read, Update, Delete) operations on the `Transaction` entity.
 * <p>
 * 1. `List<Transaction> findAll()`: This method retrieves all `Transaction` entities from the repository.
 * It returns a list of all `Transaction` entities in the database.
 * <p>
 * 2. `Optional<Transaction> findTransactionById(UUID id)`: This method retrieves a `Transaction` entity by its unique identifier (`id`).
 * It returns an `Optional` that may contain the found `Transaction` or be empty if no `Transaction` with the specified `id` exists.
 * <p>
 * The `JpaRepository` interface provides additional methods for common data access operations,
 * such as saving entities, deleting entities, and performing pagination and sorting.
 * <p>
 * By extending the `JpaRepository` interface and specifying the `Transaction` entity class
 * and the type of its primary key (`UUID`), the `TransactionRepository` interface inherits these methods
 * and also allows you to define custom repository methods specific to the `Transaction` entity.
 * <p>
 * With the `TransactionRepository` interface, we can easily perform database operations on the `Transaction` entity,
 * such as finding transactions by ID and accessing all transactions in the repository.
 */
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAll();

    Optional<Transaction> findTransactionById(UUID id);
}