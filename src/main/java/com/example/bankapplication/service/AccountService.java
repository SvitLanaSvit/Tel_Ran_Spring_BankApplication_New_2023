package com.example.bankapplication.service;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.AccountListDTO;

import java.util.UUID;

public interface AccountService {
    AccountDTO getAccountById(UUID id);
    AccountListDTO getAllAccountsStatus();
}
