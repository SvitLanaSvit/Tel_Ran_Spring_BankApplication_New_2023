package com.example.bankapplication.entity;

import com.example.bankapplication.entity.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

/**
 * The provided code snippet defines a class that represents an entity in a database using
 * the JPA (Java Persistence API) annotations.
 *
 * `@Entity`: This annotation marks the class as a JPA entity, indicating that it corresponds to a table in the database.
 *
 * `@Table(name = "clients")`: This annotation specifies the name of the database table to which the entity is mapped.
 * In this case, the entity is mapped to the "agreements" table.
 *
 * `@Getter` and `@Setter`: These annotations are from the Lombok library and automatically generate getter
 * and setter methods for the fields in the class.
 *
 * `@NoArgsConstructor`: This annotation generates a no-argument constructor for the class.
 *
 * `@AllArgsConstructor`: This annotation generates a constructor with parameters for all fields in the class.
 *
 * By applying these annotations to the class, you define it as a JPA entity that is mapped
 * to the "accounts" table in the database. The generated getter and setter methods provide access to the entity's fields,
 * and the constructors allow creating instances of the entity with or without arguments.
 *
 * @Id: This annotation is used to mark a field as the primary key of the entity.
 *
 * @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID):
 * This annotation configures the generation strategy for the primary key field.
 * In this case, it specifies the use of a UUID (Universally Unique Identifier) generator
 * to generate unique identifiers for each entity. The GenerationType.UUID strategy indicates that the UUID values
 * will be generated using the UUID algorithm.
 *
 * @Column: This annotation is used to specify the mapping between the field and the corresponding column
 * in the database table. When used without any parameters, it assumes that the column name is the same as the field name.
 *
 * @Enumerated(EnumType.STRING): This annotation is used to specify how an enumerated type is mapped to the database.
 * In this case, it indicates that the enumerated type should be stored as a string in the database.
 *
 * @ManyToOne: This annotation specifies a many-to-one relationship between entities.
 * It indicates that the current entity (e.g., Client) has a many-to-one association
 * with another entity (e.g., Manager).
 * The cascade attribute specifies the cascading behavior for related entities, and the fetch attribute determines
 * the fetching strategy for the association.
 *
 * @JoinColumn: This annotation is used in conjunction with @ManyToOne to specify the join column for the relationship.
 * It indicates the column name (name) in the current entity's table that references
 * the foreign key column (referencedColumnName) in the referenced entity's table.
 *
 * @OneToMany: This annotation specifies a one-to-many relationship between entities.
 *  It indicates that the current entity (e.g., Client) has a one-to-many association with
 *  a collection of another entity (e.g., Account).
 *  The `mappedBy` attribute specifies the field in the target entity that owns the relationship,
 *  and the `cascade` attribute specifies the cascading behavior for related entities.
 *  The `orphanRemoval` attribute is set to true, indicating that when an entity is removed from the collection,
 *  it should be deleted from the database.
 *
 *  fetch = FetchType.LAZY: This attribute specifies the fetching strategy for the association.
 *  In this case, it is set to LAZY, which means that the related entities (e.g., Client, Agreement, Transaction)
 *  will be loaded from the database only when they are accessed explicitly.
 *
 *  The FetchType.EAGER annotation attribute is used to specify the fetching strategy for an association in JPA.
 *  When FetchType.EAGER is used, it indicates that the associated entity or collection should be eagerly fetched
 *  from the database along with the owning entity.
 */
@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ClientStatus status;
    @Column(name = "tax_code")
    private String taxCode;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "client", cascade = {MERGE, PERSIST, REFRESH}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Account> accounts;

    @ManyToOne(cascade = {MERGE, PERSIST, REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName)
                && Objects.equals(email, client.email) && Objects.equals(phone, client.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phone);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", status=" + status +
                ", taxCode='" + taxCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", manager=" + manager +
                '}';
    }
}