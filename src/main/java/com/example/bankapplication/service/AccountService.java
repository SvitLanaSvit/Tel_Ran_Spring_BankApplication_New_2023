package com.example.bankapplication.service;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.AccountListDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.entity.Client;

import java.util.UUID;

public interface AccountService {
    AccountDTO getAccountById(UUID id);
    AccountListDTO getAllAccountsStatus();
    AccountDTO createAccount(CreateAccountDTO dto);
    void deleteAccountById(UUID id);

}
