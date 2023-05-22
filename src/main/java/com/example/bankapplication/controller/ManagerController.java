package com.example.bankapplication.controller;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerInfoDTO;
import com.example.bankapplication.dto.ManagerListDTO;
import com.example.bankapplication.entity.enums.ManagerStatus;
import com.example.bankapplication.service.ManagerService;
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
public class ManagerController {
    private final ManagerService managerService;
    private final RequestService requestService;

    @PostMapping("createManager")
    public ManagerDTO create(@RequestBody CreateManagerDTO manager){
        return managerService.create(manager);
    }

    @GetMapping("manager/{id:[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}}")
    @ResponseStatus(HttpStatus.OK)
    public ManagerDTO getManagerById(@PathVariable UUID id){
        return managerService.getManagerById(id);
    }

    @GetMapping("managers")
    @ResponseStatus(HttpStatus.OK)
    public ManagerListDTO getAllManagers(){
        return managerService.getManagersStatus();
    }

    @GetMapping("managers/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ManagerListDTO getAllManagersByStatus(@PathVariable String status){
        String statusUp = status.toUpperCase();
        return managerService.getAllManagersByStatus(ManagerStatus.valueOf(statusUp));
    }

    @DeleteMapping("deleteManager/{id}")
    public void delete(@PathVariable UUID id){
        managerService.deleteById(id);
    }

    @PutMapping("editManager/{id}")
    public ManagerDTO editManager(@PathVariable UUID id, @RequestBody CreateManagerDTO dto){
        return managerService.editManagerById(id, dto);
    }

    @GetMapping("managers/all")
    @ResponseStatus(HttpStatus.OK)
    public ManagerListDTO getAll(){
        return managerService.getAll();
    }

    @GetMapping("managers/productsQuantity")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ManagerInfoDTO> getAllManagersSortedByProductQuantity(){
        return requestService.findAllManagersSortedByProductQuantity();
    }

    @GetMapping("managersJPA/productsQuantity")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ManagerInfoDTO> getAllManagersSortedByProductQuantityJPA(){
        return managerService.findAllManagersSortedByProductQuantity();
    }
}