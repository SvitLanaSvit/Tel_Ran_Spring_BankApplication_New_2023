package com.example.bankapplication.repository;

import com.example.bankapplication.dto.AgreementIdDTO;
import com.example.bankapplication.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

/**
 * The `AgreementRepository` interface extends the `JpaRepository` interface, which is provided by the Spring Data JPA framework.
 * This interface defines methods for performing CRUD (Create, Read, Update, Delete) operations on the `Agreement` entity.
 *
 * 1. `List<Agreement> findAll()`:
 *    This method retrieves all `Agreement` entities from the repository.
 *    It returns a list of all `Agreement` entities in the database.
 *
 * 2. `Optional<Agreement> findAgreementById(UUID id)`:
 *    This method retrieves an `Agreement` entity by its unique identifier (`id`).
 *    It returns an `Optional` that may contain the found `Agreement` or be empty if no `Agreement` with the specified `id` exists.
 *
 * @Query annotation allows you to write custom SQL queries in your Spring Data JPA repository methods.
 * 3. The findAgreementsByManagerId method is a custom repository method defined in the AgreementRepository interface.
 * It uses the @Query annotation to specify a custom SQL query that retrieves distinct AgreementIdDTO objects.
 * The query joins the Agreement table with the Product and Manager tables using the LEFT JOIN keyword.
 * It filters the results based on the managerId parameter.
 *
 * The `JpaRepository` interface provides additional methods for common data access operations,
 * such as saving entities, deleting entities, and performing pagination and sorting.
 *
 * By extending the `JpaRepository` interface and specifying the `Agreement` entity class
 * and the type of its primary key (`UUID`), the `AgreementRepository` interface inherits these methods
 * and also allows you to define custom repository methods specific to the `Agreement` entity.
 *
 * With the `AgreementRepository` interface, we can easily perform database operations on the `Agreement` entity,
 * such as finding agreements by ID and accessing all agreements in the repository.
 *
 * Spring Data JPA is a framework that simplifies the implementation of data access layers in Java applications
 * by providing advanced CRUD (Create, Read, Update, Delete) operations and query capabilities.
 * It is built on top of the Java Persistence API (JPA) and integrates with popular relational databases.
 *
 * With Spring Data JPA, we can create repositories by defining interfaces that extend specific repository interfaces
 * provided by the framework. These repository interfaces offer a wide range
 * of methods for performing common database operations, such as saving entities, retrieving entities by ID,
 * querying entities based on specific criteria, and deleting entities.
 */
public interface AgreementRepository extends JpaRepository<Agreement, UUID> {
    List<Agreement> findAll();
    Optional<Agreement> findAgreementById(UUID id);

    @Query("SELECT distinct new com.example.bankapplication.dto.AgreementIdDTO(a.id) FROM Agreement a " +
            "LEFT JOIN Product p " +
            "LEFT JOIN Manager m " +
            "WHERE m.id = :managerId")
    List<AgreementIdDTO> findAgreementsByManagerId(@Param("managerId")UUID managerId);

    @Query("SELECT distinct new com.example.bankapplication.dto.AgreementIdDTO(ag.id) FROM Agreement ag " +
            "LEFT JOIN Account ac " +
            "LEFT JOIN Client c " +
            "WHERE c.id = :clientId")
    List<AgreementIdDTO> findAgreementsByClientId(@Param("clientId")UUID clientId);
}