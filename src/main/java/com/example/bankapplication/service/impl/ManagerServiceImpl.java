package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerListDTO;
import com.example.bankapplication.entity.enums.ManagerStatus;
import com.example.bankapplication.mapper.ManagerMapper;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.service.ManagerService;
import com.example.bankapplication.service.exception.ErrorMessage;
import com.example.bankapplication.service.exception.ManagerNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@Slf4j
//@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    @Transactional
    public ManagerDTO getManagerById(UUID id) {
        return managerMapper.toDTO(managerRepository.findManagerById(id).orElseThrow(
                () -> new ManagerNotFoundException(ErrorMessage.Manager_NOT_FOUND)
        ));
    }

    @Override
    @Transactional
    public ManagerListDTO getManagersStatus() {
        return new ManagerListDTO(managerMapper.managersToManagersDTO(managerRepository.getAllByStatus(ManagerStatus.ACTIVE)));
    }

    @Override
    @Transactional
    public ManagerDTO create(CreateManagerDTO dto) {
        log.info("Creating manager");
        var manager = managerMapper.createToEntity(dto);
        var result = managerRepository.save(manager);
        return managerMapper.toDTO(result);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        log.info("Deleting manager {}", id);
        managerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ManagerDTO editManagerById(UUID id, CreateManagerDTO dto) {
        log.info("Edit manager {}", id);
        var manager = managerRepository.findManagerById(id).orElseThrow(
                () -> new ManagerNotFoundException(ErrorMessage.Manager_NOT_FOUND)
        );
        log.info("Id manager: " + manager.getId());
        log.info("Firstname: " + dto.getFirstName());
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setStatus(dto.getStatus());
        manager.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        var result = managerRepository.save(manager);
        return managerMapper.toDTO(result);
    }
}
