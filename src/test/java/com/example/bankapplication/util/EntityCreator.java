package com.example.bankapplication.util;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.entity.*;
import com.example.bankapplication.entity.enums.ManagerStatus;

import java.sql.Timestamp;
import java.util.UUID;

public class EntityCreator {
    public static Manager getManager(UUID id){
        Manager manager = new Manager();
        manager.setId(id);
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setStatus(ManagerStatus.valueOf("ACTIVE"));
        manager.setCreatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        manager.setUpdatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        return manager;
    }

    public static Manager getManagerAfterDTO(UUID id, CreateManagerDTO dto){
        Manager manager = new Manager();
        manager.setId(id);
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setStatus(ManagerStatus.valueOf(dto.getStatus()));
        manager.setCreatedAt(dto.getCreatedAt());
        manager.setUpdatedAt(dto.getUpdatedAt());
        return manager;
    }
}
