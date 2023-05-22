package com.example.bankapplication.controller;

import com.example.bankapplication.dto.*;
import com.example.bankapplication.entity.enums.ProductStatus;
import com.example.bankapplication.service.AccountService;
import com.example.bankapplication.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

/**
 * The @RestController annotation indicates that this class is a controller that handles HTTP requests
 * and produces JSON or XML responses.
 *
 * The @RequestMapping("/auth") annotation specifies the base URL path for all the endpoints in this controller,
 * which will be "/auth".
 *
 * The @RequiredArgsConstructor annotation is a Lombok annotation that generates a constructor
 * with required arguments for the class fields marked with final.
 *
 * The @PostMapping annotation is used to map HTTP POST requests to specific methods in a controller class.
 * It defines the URL path for the endpoint and specifies the logic to handle the POST request and generate the response.
 *
 *The @GetMapping annotation is used to map HTTP GET requests to specific methods in a controller class.
 * It specifies the URL path for the endpoint and defines the logic to handle the GET request and generate the response.
 *
 *The @DeleteMapping annotation is used to map HTTP DELETE requests to specific methods in a controller class.
 * It specifies the URL path for the endpoint and defines the logic to handle the DELETE request.
 *
 *The @PutMapping annotation is used to map HTTP PUT requests to specific methods in a controller class.
 * It specifies the URL path for the endpoint and defines the logic to handle the PUT request.
 *
 * The @ResponseStatus annotation is used to specify the default HTTP response status code for
 * a particular method or exception handler in a controller class.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final RequestService requestService;

    @PostMapping("/createAccount")
    public AccountDTO createAccount(@RequestBody CreateAccountDTO account){
        return accountService.createAccount(account);
    }

    @GetMapping("/account/{id}")
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

    @GetMapping("accounts/all")
    @ResponseStatus(HttpStatus.OK)
    public AccountListDTO getAll(){
        return accountService.getAll();
    }

    @GetMapping("findAccounts")
    @ResponseStatus(HttpStatus.OK)
    public Collection<AccountIdDTO> getAccountIdsByProductIdAndStatus(
            @RequestParam UUID productId, @RequestParam ProductStatus status) {
       return requestService.findAccountsByProductIdAndStatus(productId,status);
    }

    @GetMapping("findAccountsJPA")
    @ResponseStatus(HttpStatus.OK)
    public Collection<AccountIdDTO> getAccountIdsByProductIdAndStatusQuery(
            @RequestParam UUID id, @RequestParam ProductStatus status){
        return accountService.findAccountsByProductIdAndStatus(id, status);
    }
}