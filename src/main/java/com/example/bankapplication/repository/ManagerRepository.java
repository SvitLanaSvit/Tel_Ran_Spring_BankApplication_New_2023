package com.example.bankapplication.repository;

import com.example.bankapplication.dto.ManagerInfoDTO;
import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.entity.enums.ManagerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The `ManagerRepository` interface extends the `JpaRepository` interface, which is provided by the Spring Data JPA framework.
 * This interface defines methods for performing CRUD (Create, Read, Update, Delete) operations on the `Manager` entity.
 *
 * 1. `Optional<Manager> findManagerById(UUID id)`:
 * This method retrieves a `Manager` entity by its unique identifier (`id`).
 * It returns an `Optional` that may contain the found `Manager` or be empty if no `Manager` with the specified `id` exists.
 *
 * 2. `List<Manager> getAllByStatus(ManagerStatus managerStatus)`:
 * This method retrieves a list of `Manager` entities that have the specified `managerStatus`.
 * It returns all `Manager` entities that match the given `managerStatus`.
 *
 * 3. `List<Manager> findByStatus(ManagerStatus status)`:
 * This method retrieves a list of `Manager` entities that have the specified `status`.
 * It returns all `Manager` entities that match the given `status`.
 *
 * 4. `List<Manager> findAll()`: This method retrieves all `Manager` entities from the repository.
 * It returns a list of all `Manager` entities in the database.
 *
 * The `JpaRepository` interface provides additional methods for common data access operations,
 * such as saving entities, deleting entities, and performing pagination and sorting.
 *
 * By extending the `JpaRepository` interface and specifying the `Manager` entity class
 * and the type of its primary key (`UUID`), the `ManagerRepository` interface inherits these methods
 * and also allows you to define custom repository methods specific to the `Manager` entity.
 *
 * With the `ManagerRepository` interface, we can easily perform database operations on the `Manager` entity,
 * such as finding managers by ID, retrieving managers by status, and accessing all managers in the repository.
 */
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    Optional<Manager> findManagerById(UUID id);
    List<Manager> getAllByStatus(ManagerStatus managerStatus);
    List<Manager> findByStatus(ManagerStatus status);
    List<Manager> findAll();

    @Query("SELECT m FROM Manager m LEFT JOIN FETCH m.products p ORDER BY p.productLimit")
    List<Manager> findAllManagersSortedByProductQuantity();
}