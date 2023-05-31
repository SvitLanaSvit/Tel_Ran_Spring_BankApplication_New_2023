package com.example.bankapplication.entity;

import com.example.bankapplication.entity.enums.AgreementStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * The provided code snippet defines a class that represents an entity in a database using
 * the JPA (Java Persistence API) annotations.
 * <p>
 * `@Entity`: This annotation marks the class as a JPA entity, indicating that it corresponds to a table in the database.
 * <p>
 * `@Table(name = "agreements")`: This annotation specifies the name of the database table to which the entity is mapped.
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
 * @ManyToOne: This annotation specifies a many-to-one relationship between entities.
 * It indicates that the current entity (e.g., Agreement) has a many-to-one association
 * with another entity (e.g., Product and Account).
 * The cascade attribute specifies the cascading behavior for related entities, and the fetch attribute determines
 * the fetching strategy for the association.
 * @JoinColumn: This annotation is used in conjunction with @ManyToOne to specify the join column for the relationship.
 * It indicates the column name (name) in the current entity's table that references
 * the foreign key column (referencedColumnName) in the referenced entity's table.
 */
@Entity
@Table(name = "agreements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agreement {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "interest_rate")
    private double interestRate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AgreementStatus status;
    @Column(name = "sum")
    private double sum;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agreement agreement = (Agreement) o;
        return id == agreement.id && Objects.equals(createdAt, agreement.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt);
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                ", interestRate=" + interestRate +
                ", status=" + status +
                ", sum=" + sum +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", product=" + product +
                ", account=" + account +
                '}';
    }
}