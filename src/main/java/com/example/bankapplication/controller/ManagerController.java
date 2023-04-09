package com.example.bankapplication.controller;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerListDTO;
import com.example.bankapplication.entity.enums.ManagerStatus;
import com.example.bankapplication.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    @PostMapping("createManager")
    public ManagerDTO create(@RequestBody CreateManagerDTO manager){
        return managerService.create(manager);
    }

    @RequestMapping("managers/{id:[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}}")
    @ResponseStatus(HttpStatus.OK)
    public ManagerDTO getManagerById(@PathVariable UUID id){
        return managerService.getManagerById(id);
    }

    @RequestMapping("managers")
    @ResponseStatus(HttpStatus.OK)
    public ManagerListDTO getAllManagers(){
        return managerService.getManagersStatus();
    }

    @RequestMapping("managers/status/{status}")
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

    @RequestMapping("managers/all")
    @ResponseStatus(HttpStatus.OK)
    public ManagerListDTO getAll(){
        return managerService.getAll();
    }
}