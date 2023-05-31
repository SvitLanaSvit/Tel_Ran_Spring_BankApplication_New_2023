package com.example.bankapplication.repository;

import com.example.bankapplication.dto.AccountIdDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.entity.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The `AccountRepository` interface extends the `JpaRepository` interface, which is provided by the Spring Data JPA framework.
 * This interface defines methods for performing CRUD (Create, Read, Update, Delete) operations on the `Account` entity.
 * <p>
 * 1. `Optional<Account> findAccountById(UUID id)`:
 * This method retrieves an `Account` entity by its unique identifier (`id`).
 * It returns an `Optional` that may contain the found `Account` or be empty if no `Account` with the specified `id` exists.
 * <p>
 * 2. `List<Account> getAllByStatus(AccountStatus status)`:
 * This method retrieves a list of `Account` entities that match the specified `status`.
 * It returns all `Account` entities that have the given `status`.
 * <p>
 * 3. `List<Account> findAll()`: This method retrieves all `Account` entities from the repository.
 * It returns a list of all `Account` entities in the database.
 *
 * @Query annotation allows you to write custom SQL queries in your Spring Data JPA repository methods.
 * 4. The findAccountByProductIdByStatus method uses the @Query annotation to define a custom SQL query.
 * It selects new instances of AccountIdDTO using the id field from the Account entity.
 * The query includes several LEFT JOIN statements to join the Account, Client, Manager, and Product entities.
 * It filters the results based on the id and status parameters.
 * <p>
 * The `JpaRepository` interface provides additional methods for common data access operations,
 * such as saving entities, deleting entities, and performing pagination and sorting.
 * <p>
 * By extending the `JpaRepository` interface and specifying the `Account` entity class
 * and the type of its primary key (`UUID`), the `AccountRepository` interface inherits these methods
 * and also allows you to define custom repository methods specific to the `Account` entity.
 * <p>
 * With the `AccountRepository` interface, we can easily perform database operations on the `Account` entity,
 * such as finding accounts by ID, retrieving accounts by status, and accessing all accounts in the repository.
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findAccountById(UUID id);

    List<Account> getAllByStatus(AccountStatus status);

    List<Account> findAll();

    @Query("SELECT new com.example.bankapplication.dto.AccountIdDTO(a.id) FROM Account a " +
            "LEFT JOIN Client c " +
            "LEFT JOIN Manager m " +
            "LEFT JOIN Product p " +
            "WHERE p.id = :id AND p.status = :status")
    List<AccountIdDTO> findAccountByProductIdByStatus(@Param("id") UUID id, @Param("status") ProductStatus status);
}