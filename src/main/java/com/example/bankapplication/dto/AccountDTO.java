package com.example.bankapplication.dto;

import com.example.bankapplication.entity.Client;
import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.entity.enums.AccountType;
import com.example.bankapplication.entity.enums.CurrencyCode;
import lombok.Value;

import java.sql.Timestamp;
import java.util.UUID;

@Value
public class AccountDTO {
    UUID id;
    String name;
    AccountType type;
    AccountStatus status;
    Double balance;
    CurrencyCode currencyCode;
    Timestamp createAt;
    Timestamp updateAt;
    Client client;
}
