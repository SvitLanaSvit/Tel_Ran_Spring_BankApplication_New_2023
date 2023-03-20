package com.example.bankapplication.dto;

import lombok.Value;

import java.util.List;

@Value
public class AccountListDTO {
    List<AccountDTO> accountDTOList;
}
