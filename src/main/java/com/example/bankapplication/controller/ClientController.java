package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientInfoDTO;
import com.example.bankapplication.dto.ClientListDTO;
import com.example.bankapplication.dto.CreateClientDTO;
import com.example.bankapplication.service.ClientService;
import com.example.bankapplication.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final RequestService requestService;

    @PostMapping("/createClient")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO createClient(@RequestBody CreateClientDTO dto){
        return clientService.createClient(dto);
    }

    @GetMapping("/client/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO getClientBiId(@PathVariable UUID id){
        return clientService.getClientById(id);
    }

    @GetMapping("/clients/active")
    @ResponseStatus(HttpStatus.OK)
    public ClientListDTO getAllClients(){
        return clientService.getClientsStatus();
    }

    @DeleteMapping("deleteClient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@PathVariable UUID id){
        clientService.deleteClientById(id);
    }

    @PutMapping("editClient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO editClient(@PathVariable UUID id, @RequestBody CreateClientDTO dto){
        return clientService.editClientById(id, dto);
    }

    @GetMapping("clients/all")
    @ResponseStatus(HttpStatus.OK)
    public ClientListDTO getAll(){
        return clientService.getAll();
    }

    @GetMapping("clients/balanceMore")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ClientInfoDTO> getAllClientsWhereBalanceMoreThan(@RequestParam Double balance){
        return requestService.findClientsWhereBalanceMoreThan(balance);
    }
}