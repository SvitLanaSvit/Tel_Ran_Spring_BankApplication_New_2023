package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerListDTO;
import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.entity.enums.ManagerStatus;
import com.example.bankapplication.mapper.ManagerMapper;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {

    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private ManagerMapper managerMapper;
    @InjectMocks
    private ManagerServiceImpl service;

    @Test
    void getManagerById() {
        UUID id = UUID.randomUUID();
        Manager manager = EntityCreator.getManager(id);
        ManagerDTO managerDTO = DTOCreator.getManagerDTO(id);

        when(managerRepository.findManagerById(id)).thenReturn(Optional.of(manager));
        when(managerMapper.toDTO(manager)).thenReturn(managerDTO);

        ManagerDTO result = service.getManagerById(id);

        verify(managerRepository).findManagerById(id);
        verify(managerMapper).toDTO(manager);
        assertEquals(managerDTO, result);
    }

    @Test
    void getManagersStatus() {
        UUID id = UUID.randomUUID();
        List<Manager> managerList = new ArrayList<>();
        managerList.add(EntityCreator.getManager(id));

        List<ManagerDTO> managerDTOList = new ArrayList<>();
        managerDTOList.add(DTOCreator.getManagerDTO(id));

        ManagerListDTO listDTO = new ManagerListDTO(managerDTOList);

        when(managerRepository.getAllByStatus(ManagerStatus.ACTIVE)).thenReturn(managerList);
        when(managerMapper.managersToManagersDTO(managerList)).thenReturn(managerDTOList);

        ManagerListDTO result = service.getManagersStatus();

        verify(managerRepository).getAllByStatus(ManagerStatus.ACTIVE);
        verify(managerMapper).managersToManagersDTO(managerList);
        assertEquals(listDTO, result);
    }

    @Test
    void create() {
        UUID id = UUID.randomUUID();
        CreateManagerDTO createManagerDTO = DTOCreator.getManagerToCreate();
        System.out.println("createManagerDTO name: " + createManagerDTO.getFirstName());

        Manager manager = EntityCreator.getManagerAfterDTO(id, createManagerDTO); //null
        System.out.println("Manager: " + manager);

        when(managerMapper.createToEntity(createManagerDTO)).thenReturn(manager);
        when(managerRepository.save(any())).thenReturn(manager);

        ManagerDTO result = service.create(createManagerDTO);
        System.out.println("Result: " + result);

        verify(managerRepository, times(1)).save(manager);

        assertNotNull(result);
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        Manager manager = EntityCreator.getManager(id);
        managerRepository.save(manager);

        service.deleteById(manager.getId());
        verify(managerRepository, times(1)).deleteById(manager.getId());
    }

    @Test
    void editManagerById() {
        UUID id = UUID.randomUUID();
        Manager managerFromDB = EntityCreator.getManager(id);
        System.out.println(managerFromDB);

        when(managerRepository.findManagerById(id)).thenReturn(Optional.of(managerFromDB));

        CreateManagerDTO managerDTO = DTOCreator.getManagerToCreate();
        System.out.println(managerDTO.getFirstName());

        when(managerRepository.save(managerFromDB)).thenReturn(managerFromDB);

        ManagerDTO result = service.editManagerById(id, managerDTO);
        System.out.println(result);

        assertNotNull(result); //null
    }
}