package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.AccountListDTO;
import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.service.AccountService;
import com.example.bankapplication.service.exception.AccountNotFoundException;
import com.example.bankapplication.service.exception.ErrorMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountDTO getAccountById(UUID id) {
        log.info("Get an account with id {}", id);
//        var account = accountRepository.findById(id);
//        log.info(" " + account);
        return accountMapper.toDTO(accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND)
        ));
    }

    @Override
    public AccountListDTO getAllAccountsStatus() {
        log.info("Get all active accounts");
        return new AccountListDTO(accountMapper.accountsToAccountsDTO(accountRepository.getAllByStatus(AccountStatus.ACTIVE)));
    }
}
