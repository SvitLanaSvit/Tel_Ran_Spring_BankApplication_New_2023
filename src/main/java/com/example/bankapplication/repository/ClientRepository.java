package com.example.bankapplication.repository;

import com.example.bankapplication.dto.ClientInfoDTO;
import com.example.bankapplication.entity.Client;
import com.example.bankapplication.entity.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The `ClientRepository` interface extends the `JpaRepository` interface, which is provided by the Spring Data JPA framework.
 * This interface defines methods for performing CRUD (Create, Read, Update, Delete) operations on the `Client` entity.
 *
 * 1. `Optional<Client> findClientById(UUID id)`:
 * This method retrieves a `Client` entity by its unique identifier (`id`).
 * It returns an `Optional` that may contain the found `Client` or be empty if no `Client` with the specified `id` exists.
 *
 * 2. `Optional<Client> findClientByTaxCode(String taxCode)`:
 * This method retrieves a `Client` entity by its tax code.
 * It returns an `Optional` that may contain the found `Client` or be empty if no `Client` with the specified tax code exists.
 *
 * 3. `List<Client> getAllByStatus(ClientStatus status)`:
 * This method retrieves a list of `Client` entities that match the specified status.
 * It returns all `Client` entities that have the given status.
 *
 * 4. `List<Client> findAll()`: This method retrieves all `Client` entities from the repository.
 * It returns a list of all `Client` entities in the database.
 *
 * The `JpaRepository` interface provides additional methods for common data access operations,
 * such as saving entities, deleting entities, and performing pagination and sorting.
 *
 * By extending the `JpaRepository` interface and specifying the `Client` entity class
 * and the type of its primary key (`UUID`), the `ClientRepository` interface inherits these methods
 * and also allows you to define custom repository methods specific to the `Client` entity.
 *
 * With the `ClientRepository` interface, we can easily perform database operations on the `Client` entity,
 * such as finding clients by ID, finding clients by tax code, retrieving clients by status, and accessing all clients in the repository.
 */
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findClientById(UUID id);
    Optional<Client> findClientByTaxCode(String taxCode);
    List<Client> getAllByStatus(ClientStatus status);
    List<Client> findAll();

    @Query("SELECT distinct c FROM Client c LEFT JOIN FETCH c.accounts a WHERE a.balance > :balance")
    List<Client> findClientWhereBalanceMoreThan(@Param("balance") Double balance);
}
