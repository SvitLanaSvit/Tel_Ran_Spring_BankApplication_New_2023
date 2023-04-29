package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerListDTO;
import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.entity.enums.ManagerStatus;
import com.example.bankapplication.mapper.ManagerMapper;
import com.example.bankapplication.mapper.ManagerMapperImpl;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.service.exception.ManagerNotFoundException;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private ManagerMapper managerMapper ;
    private ManagerServiceImpl service;

    @BeforeEach
    void setUp(){
        managerMapper = new ManagerMapperImpl();
        service = new ManagerServiceImpl(managerRepository, managerMapper);
    }

    @Test
    @DisplayName("Positive test. Get manager by Id.")
    void testGetManagerById() {
        UUID id = UUID.randomUUID();
        Manager manager = EntityCreator.getManager(id);
        ManagerDTO managerDTO = DTOCreator.getManagerDTO(id);

        when(managerRepository.findManagerById(id)).thenReturn(Optional.of(manager));

        ManagerDTO result = service.getManagerById(id);

        verify(managerRepository).findManagerById(id);
        assertEquals(managerDTO, result);
    }

    @Test
    @DisplayName("Positive test. Get manager by status.")
    void testGetManagersStatus() {
        UUID id = UUID.randomUUID();
        List<Manager> managerList = new ArrayList<>();
        managerList.add(EntityCreator.getManager(id));

        List<ManagerDTO> managerDTOList = new ArrayList<>();
        managerDTOList.add(DTOCreator.getManagerDTO(id));

        ManagerListDTO listDTO = new ManagerListDTO(managerDTOList);

        when(managerRepository.getAllByStatus(ManagerStatus.ACTIVE)).thenReturn(managerList);

        ManagerListDTO result = service.getManagersStatus();

        verify(managerRepository).getAllByStatus(ManagerStatus.ACTIVE);
        assertEquals(listDTO.getManagerDTOList(), result.getManagerDTOList());
    }

    @Test
    @DisplayName("Positive test. Create new manager.")
    void testCreate() {
        UUID id = UUID.randomUUID();
        CreateManagerDTO createManagerDTO = DTOCreator.getManagerToCreate();
        Manager expectedManager = EntityCreator.getManagerAfterDTO(id, createManagerDTO);
        ManagerDTO expectedManagerDTO = DTOCreator.getManagerDTO(id);

        when(managerRepository.save(any(Manager.class))).thenReturn(expectedManager);

        ManagerDTO actualManagerDTO = service.create(createManagerDTO);
        assertNotNull(actualManagerDTO);
        assertEquals(expectedManagerDTO.getId(), actualManagerDTO.getId());
        assertEquals(expectedManagerDTO.getFirstName(), actualManagerDTO.getFirstName());
        assertEquals(expectedManagerDTO.getLastName(), actualManagerDTO.getLastName());
        assertEquals(expectedManagerDTO.getStatus(), actualManagerDTO.getStatus());
        assertEquals(expectedManagerDTO.getCreatedAt(), actualManagerDTO.getCreatedAt());
        assertEquals(expectedManagerDTO.getUpdatedAt(), actualManagerDTO.getUpdatedAt());
    }

    @Test
    @DisplayName("Positive test. Delete manager by Id.")
    void testDeleteById() {
        UUID managerId = UUID.randomUUID();
        when(managerRepository.findManagerById(managerId)).thenReturn(Optional.of(EntityCreator.getManager(managerId)));

        service.deleteById(managerId);
        verify(managerRepository, times(1)).deleteById(managerId);
    }

    @Test
    @DisplayName("Positive test. Edit manager by Id.")
    void testEditManagerById() {
        UUID id = UUID.randomUUID();
        CreateManagerDTO dto = DTOCreator.getManagerToCreate();
        Manager manager = EntityCreator.getManager(id);
        ManagerDTO expected = DTOCreator.getManagerDTO(id);

        when(managerRepository.findManagerById(id)).thenReturn(Optional.of(manager));
        when(managerRepository.save(any())).thenReturn(manager);

        ManagerDTO actual = service.editManagerById(id, dto);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertNotNull(actual.getUpdatedAt());

        verify(managerRepository, times(1)).findManagerById(id);
        verify(managerRepository, times(1)).save(manager);
    }

    @Test
    @DisplayName("Negative test. Not found manager by Id.")
    public void editManagerById_shouldThrowExceptionWhenManagerNotFound() {
        UUID id = UUID.randomUUID();
        CreateManagerDTO dto = DTOCreator.getManagerToCreate();

        when(managerRepository.findManagerById(id)).thenReturn(Optional.empty());

        assertThrows(ManagerNotFoundException.class, () -> service.editManagerById(id, dto));
    }

    @Test
    @DisplayName("Positive test. Found all managers by status ACTIVE.")
    public void testGetAllManagersByStatus() {
        UUID id = UUID.randomUUID();
        List<Manager> managerList = new ArrayList<>();
        managerList.add(EntityCreator.getManager(id));

        List<ManagerDTO> managerDTOList = new ArrayList<>();
        managerDTOList.add(DTOCreator.getManagerDTO(id));

        ManagerListDTO listDTO = new ManagerListDTO(managerDTOList);
        when(managerRepository.findByStatus(ManagerStatus.ACTIVE)).thenReturn(managerList);

        ManagerListDTO dto = service.getAllManagersByStatus(ManagerStatus.ACTIVE);

        verify(managerRepository).findByStatus(ManagerStatus.ACTIVE);
        assertEquals(dto.getManagerDTOList(), listDTO.getManagerDTOList());
    }

    @Test
    @DisplayName("Positive test. Get all managers.")
    void testGetAll(){
        UUID id = UUID.randomUUID();
        List<Manager> managerList = new ArrayList<>();
        managerList.add(EntityCreator.getManager(id));

        List<ManagerDTO> managerDTOList = new ArrayList<>();
        managerDTOList.add(DTOCreator.getManagerDTO(id));

        ManagerListDTO listDTO = new ManagerListDTO(managerDTOList);

        when(managerRepository.findAll()).thenReturn(managerList);

        ManagerListDTO result = service.getAll();

        verify(managerRepository).findAll();
        assertEquals(listDTO.getManagerDTOList(), result.getManagerDTOList());
    }
}