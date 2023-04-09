package com.example.bankapplication.controller;

import com.example.bankapplication.dto.AgreementListDTO;
import com.example.bankapplication.service.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AgreementController {
    private final AgreementService agreementService;

    @GetMapping("agreements/all")
    @ResponseStatus(HttpStatus.OK)
    public AgreementListDTO getAll(){
        return agreementService.getAll();
    }
}
