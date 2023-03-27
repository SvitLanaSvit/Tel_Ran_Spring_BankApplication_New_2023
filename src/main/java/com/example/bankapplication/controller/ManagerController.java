package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerListDTO;
import com.example.bankapplication.service.ManagerService;
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
public class ManagerController {
    private final ManagerService managerService;

    @RequestMapping("managers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ManagerDTO getManagerById(@PathVariable UUID id){
        return managerService.getManagerById(id);
    }

    @RequestMapping("managers")
    @ResponseStatus(HttpStatus.OK)
    public ManagerListDTO getAllManagers(){
        return managerService.getManagersStatus();
    }
}
