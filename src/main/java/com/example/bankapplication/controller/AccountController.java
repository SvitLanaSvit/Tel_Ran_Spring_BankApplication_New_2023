package com.example.bankapplication.controller;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.AccountListDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/createAccount")
    public AccountDTO createAccount(@RequestBody CreateAccountDTO account){
        return accountService.createAccount(account);
    }

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getAccountById(@PathVariable UUID id){
        return accountService.getAccountById(id);
    }

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public AccountListDTO getAllAccounts(){
        return accountService.getAllAccountsStatus();
    }

    @DeleteMapping("deleteAccount/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable UUID id){
        accountService.deleteAccountById(id);
    }

    @PutMapping("editAccount/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO editAccount(@PathVariable UUID id, @RequestBody CreateAccountDTO dto){
        return accountService.editAccountById(id, dto);
    }

    @RequestMapping("accounts/all")
    @ResponseStatus(HttpStatus.OK)
    public AccountListDTO getAll(){
        return accountService.getAll();
    }
}