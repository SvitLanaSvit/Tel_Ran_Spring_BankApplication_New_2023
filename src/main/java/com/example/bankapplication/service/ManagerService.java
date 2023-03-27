package com.example.bankapplication.service;

import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerListDTO;
import java.util.UUID;

public interface ManagerService {
    ManagerDTO getManagerById(UUID id);
    ManagerListDTO getManagersStatus();
}
