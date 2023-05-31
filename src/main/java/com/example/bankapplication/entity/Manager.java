package com.example.bankapplication.entity;

import com.example.bankapplication.entity.enums.ManagerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

/**
 * The provided code snippet defines a class that represents an entity in a database using
 * the JPA (Java Persistence API) annotations.
 * <p>
 * `@Entity`: This annotation marks the class as a JPA entity, indicating that it corresponds to a table in the database.
 * <p>
 * `@Table(name = "managers")`: This annotation specifies the name of the database table to which the entity is mapped.
 * In this case, the entity is mapped to the "agreements" table.
 * <p>
 * `@Getter` and `@Setter`: These annotations are from the Lombok library and automatically generate getter
 * and setter methods for the fields in the class.
 * <p>
 * `@NoArgsConstructor`: This annotation generates a no-argument constructor for the class.
 * <p>
 * `@AllArgsConstructor`: This annotation generates a constructor with parameters for all fields in the class.
 * <p>
 * By applying these annotations to the class, you define it as a JPA entity that is mapped
 * to the "accounts" table in the database. The generated getter and setter methods provide access to the entity's fields,
 * and the constructors allow creating instances of the entity with or without arguments.
 *
 * @Id: This annotation is used to mark a field as the primary key of the entity.
 * @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID):
 * This annotation configures the generation strategy for the primary key field.
 * In this case, it specifies the use of a UUID (Universally Unique Identifier) generator
 * to generate unique identifiers for each entity. The GenerationType.UUID strategy indicates that the UUID values
 * will be generated using the UUID algorithm.
 * @Column: This annotation is used to specify the mapping between the field and the corresponding column
 * in the database table. When used without any parameters, it assumes that the column name is the same as the field name.
 * @Enumerated(EnumType.STRING): This annotation is used to specify how an enumerated type is mapped to the database.
 * In this case, it indicates that the enumerated type should be stored as a string in the database.
 * @OneToMany: This annotation specifies a one-to-many relationship between entities.
 * It indicates that the current entity (e.g., Manager) has a one-to-many association with
 * a collection of another entity (e.g., Client, Product).
 * The `mappedBy` attribute specifies the field in the target entity that owns the relationship,
 * and the `cascade` attribute specifies the cascading behavior for related entities.
 * The `orphanRemoval` attribute is set to true, indicating that when an entity is removed from the collection,
 * it should be deleted from the database.
 * <p>
 * fetch = FetchType.LAZY: This attribute specifies the fetching strategy for the association.
 * In this case, it is set to LAZY, which means that the related entities (e.g., Client, Agreement, Transaction)
 * will be loaded from the database only when they are accessed explicitly.
 * <p>
 * The FetchType.EAGER annotation attribute is used to specify the fetching strategy for an association in JPA.
 * When FetchType.EAGER is used, it indicates that the associated entity or collection should be eagerly fetched
 * from the database along with the owning entity.
 */
@Entity
@Table(name = "managers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ManagerStatus status;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "manager", cascade = {MERGE, PERSIST, REFRESH}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Client> clients;

    @OneToMany(mappedBy = "manager", cascade = {MERGE, PERSIST, REFRESH}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(firstName, manager.firstName) && Objects.equals(lastName, manager.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}