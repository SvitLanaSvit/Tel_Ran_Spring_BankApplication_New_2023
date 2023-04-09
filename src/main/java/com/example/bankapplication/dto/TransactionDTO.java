package com.example.bankapplication.dto;

import com.example.bankapplication.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDTO {
    String id;
    String type;
    String amount;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Timestamp createdAt;

    String debitAccountId;

    String creditAccountId;
}