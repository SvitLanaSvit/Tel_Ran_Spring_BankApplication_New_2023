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

    @GetMapping("clientsJPA/balanceMore")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ClientInfoDTO> getAllClientsWhereBalanceMoreThanJPA(@RequestParam Double balance){
        return clientService.findClientsWhereBalanceMoreThan(balance);
    }
}