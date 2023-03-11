package com.example.bankapplication.entity;

import com.example.bankapplication.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "com.example.bankapplication.generator.UuidTimeSequenceGenerator")
    @Column(name = "id")
    private UUID id;
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;
    @Column(name = "amount")
    private double amount;
    @Column(name = "description")
    private String description;
    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne(cascade = {MERGE, PERSIST, REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "debit_account_id", referencedColumnName = "id")
    private Account debitAccount;

    @ManyToOne(cascade = {MERGE, PERSIST, REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_account_id", referencedColumnName = "id")
    private Account creditAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", debitAccount=" + debitAccount +
                ", creditAccount=" + creditAccount +
                '}';
    }
}