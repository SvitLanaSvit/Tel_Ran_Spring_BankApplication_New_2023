package com.example.bankapplication.controller;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.AgreementListDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.service.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("agreement/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AgreementDTO getAgreementById(@PathVariable UUID id){
        return agreementService.getAgreementById(id);
    }

    @PostMapping("createAgreement")
    @ResponseStatus(HttpStatus.OK)
    public AgreementDTO createAgreement(@RequestBody CreateAgreementDTO createAgreementDTO){
        return agreementService.createAgreement(createAgreementDTO);
    }

    @PutMapping("editAgreement/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AgreementDTO editAgreement(@PathVariable UUID id, @RequestBody CreateAgreementDTO dto){
        return agreementService.editAgreementById(id, dto);
    }

    @DeleteMapping("deleteAgreement/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAgreementById(@PathVariable UUID id){
        agreementService.deleteAgreementById(id);
    }
}
