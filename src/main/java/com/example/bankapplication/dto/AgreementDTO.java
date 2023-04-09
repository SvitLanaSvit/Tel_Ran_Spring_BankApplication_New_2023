package com.example.bankapplication.dto;

import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.Product;
import com.example.bankapplication.entity.enums.AgreementStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgreementDTO {
    String id;
    String interestRate;
    String status;
    String sum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Timestamp updatedAt;
    String productId;
    String accountId;
}
