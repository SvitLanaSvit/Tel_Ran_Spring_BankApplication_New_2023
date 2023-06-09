package com.example.bankapplication.service;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerInfoDTO;
import com.example.bankapplication.dto.ManagerListDTO;
import com.example.bankapplication.entity.enums.ManagerStatus;

import java.util.List;
import java.util.UUID;

public interface ManagerService {
    ManagerDTO getManagerById(UUID id);

    ManagerListDTO getManagersStatus();

    ManagerDTO create(CreateManagerDTO dto);

    void deleteById(UUID id);

    ManagerDTO editManagerById(UUID id, CreateManagerDTO dto);

    ManagerListDTO getAllManagersByStatus(ManagerStatus status);

    ManagerListDTO getAll();

    List<ManagerInfoDTO> findAllManagersSortedByProductQuantity();
}
