package com.example.bankapplication.controller;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.AgreementIdDTO;
import com.example.bankapplication.dto.AgreementListDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.service.AgreementService;
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
public class AgreementController {
    private final AgreementService agreementService;
    private final RequestService requestService;

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

    @GetMapping("findAgreements/ManagerId")
    @ResponseStatus(HttpStatus.OK)
    public Collection<AgreementIdDTO> getAgreementsByManagerId(@RequestParam UUID managerId){
        return requestService.findAgreementsByManagerId(managerId);
    }

    @GetMapping("findAgreementsJPA/ManagerId")
    @ResponseStatus(HttpStatus.OK)
    public Collection<AgreementIdDTO> getAgreementsByManagerIdJPA(@RequestParam UUID managerId){
        return agreementService.findAgreementsByManagerId(managerId);
    }

    @GetMapping("findAgreements/ClientId")
    @ResponseStatus(HttpStatus.OK)
    public Collection<AgreementIdDTO> getAgreementsByClientId(@RequestParam UUID clientId){
        return requestService.findAgreementByClientId(clientId);
    }

    @GetMapping("findAgreementsJPA/ClientId")
    @ResponseStatus(HttpStatus.OK)
    public Collection<AgreementIdDTO> getAgreementsByClientIdJPA(@RequestParam UUID clientId){
        return agreementService.findAgreementByClientId(clientId);
    }
}