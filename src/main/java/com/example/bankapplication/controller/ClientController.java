package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientListDTO;
import com.example.bankapplication.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @RequestMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO getClientBiId(@PathVariable UUID id){
        return clientService.getClientById(id);
    }

    @RequestMapping("/clients")
    public ClientListDTO getAllClients(){
        return clientService.getClientsStatus();
    }
}
