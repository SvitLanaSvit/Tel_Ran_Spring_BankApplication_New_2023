package com.example.bankapplication.repository;

import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The `ProductRepository` interface extends the `JpaRepository` interface, which is provided by the Spring Data JPA framework.
 * This interface defines methods for performing CRUD (Create, Read, Update, Delete) operations on the `Product` entity.
 * <p>
 * 1. `List<Product> findAll()`: This method retrieves all `Product` entities from the repository.
 * It returns a list of all `Product` entities in the database.
 * <p>
 * 2. `Optional<Product> findProductById(UUID id)`: This method retrieves a `Product` entity by its unique identifier (`id`).
 * It returns an `Optional` that may contain the found `Product` or be empty if no `Product` with the specified `id` exists.
 * <p>
 * The `JpaRepository` interface provides additional methods for common data access operations,
 * such as saving entities, deleting entities, and performing pagination and sorting.
 * <p>
 * By extending the `JpaRepository` interface and specifying the `Product` entity class
 * and the type of its primary key (`UUID`), the `ProductRepository` interface inherits these methods
 * and also allows you to define custom repository methods specific to the `Product` entity.
 * <p>
 * With the `ProductRepository` interface, we can easily perform database operations on the `Product` entity,
 * such as finding products by ID and accessing all products in the repository.
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAll();

    Optional<Product> findProductById(UUID id);

    @Query("SELECT p FROM Product p WHERE p.updatedAt is not null")
    List<Product> findAllChangedProducts();
}