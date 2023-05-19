package com.example.bankapplication.entity;

import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.entity.enums.AccountType;
import com.example.bankapplication.entity.enums.CurrencyCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

/**
 * The provided code snippet defines a class that represents an entity in a database using
 * the JPA (Java Persistence API) annotations.
 *
 * `@Entity`: This annotation marks the class as a JPA entity, indicating that it corresponds to a table in the database.
 *
 * `@Table(name = "accounts")`: This annotation specifies the name of the database table to which the entity is mapped.
 * In this case, the entity is mapped to the "accounts" table.
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
 * It indicates that the current entity (e.g., Account) has a many-to-one association with another entity (e.g., Client).
 * The cascade attribute specifies the cascading behavior for related entities, and the fetch attribute determines
 * the fetching strategy for the association.
 *
 * @JoinColumn: This annotation is used in conjunction with @ManyToOne to specify the join column for the relationship.
 * It indicates the column name (name) in the current entity's table that references
 * the foreign key column (referencedColumnName) in the referenced entity's table.
 *
 * @OneToMany: This annotation specifies a one-to-many relationship between entities.
 * It indicates that the current entity (e.g., Account) has a one-to-many association with
 * a collection of another entity (e.g., Agreement, Transaction).
 * The `mappedBy` attribute specifies the field in the target entity that owns the relationship,
 * and the `cascade` attribute specifies the cascading behavior for related entities.
 * The `orphanRemoval` attribute is set to true, indicating that when an entity is removed from the collection,
 * it should be deleted from the database.
 *
 * fetch = FetchType.LAZY: This attribute specifies the fetching strategy for the association.
 * In this case, it is set to LAZY, which means that the related entities (e.g., Client, Agreement, Transaction)
 * will be loaded from the database only when they are accessed explicitly.
 *
 * The FetchType.EAGER annotation attribute is used to specify the fetching strategy for an association in JPA.
 * When FetchType.EAGER is used, it indicates that the associated entity or collection should be eagerly fetched
 * from the database along with the owning entity.
 */
@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
/*
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "com.example.bankapplication.generator.UuidTimeSequenceGenerator")
*/
    @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @Column(name = "balance")
    private double balance;
    @Column(name = "currency_code")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(cascade = {MERGE, PERSIST, REFRESH},fetch = FetchType.LAZY) //eager
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "account", cascade = {MERGE, PERSIST, REFRESH}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Agreement> agreements;

    @OneToMany(mappedBy = "debitAccount", cascade = {MERGE, PERSIST, REFRESH}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Transaction> debitTransactions;
    @OneToMany(mappedBy = "creditAccount", cascade = {MERGE, PERSIST, REFRESH}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Transaction> creditTransactions;

    public Account(UUID id, String name, AccountType type, AccountStatus status, double balance, CurrencyCode currencyCode, Timestamp createdAt, Timestamp updatedAt, Client client) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.balance = balance;
        this.currencyCode = currencyCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", balance=" + balance +
                ", currencyCode=" + currencyCode +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", client=" + client +
                '}';
    }
}