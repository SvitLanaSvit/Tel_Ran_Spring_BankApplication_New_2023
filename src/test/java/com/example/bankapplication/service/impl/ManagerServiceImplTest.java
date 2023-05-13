package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.*;
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
@DisplayName("Test class for ManagerServiceImpl")
class ManagerServiceImplTest {

    @Mock
    private ManagerRepository managerRepository;
    private ManagerMapper managerMapper ;
    private ManagerServiceImpl managerService;

    private UUID uuid;
    private Manager manager;
    private  List<Manager> managerList;
    private ManagerDTO managerDTO;
    private List<ManagerDTO> managerDTOList;
    private CreateManagerDTO createManagerDTO;

    @BeforeEach
    void setUp(){
        managerMapper = new ManagerMapperImpl();
        managerService = new ManagerServiceImpl(managerRepository, managerMapper);
        uuid = UUID.randomUUID();
        manager = EntityCreator.getManager(uuid);
        managerDTO = DTOCreator.getManagerDTO(uuid);
        managerList = new ArrayList<>(List.of(manager));
        managerDTOList = new ArrayList<>(List.of(managerDTO));
        createManagerDTO = DTOCreator.getManagerToCreate();
    }

    @Test
    @DisplayName("Positive test. Get manager by Id.")
    void testGetManagerById() {
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.of(manager));

        ManagerDTO result = managerService.getManagerById(uuid);

        verify(managerRepository).findManagerById(any(UUID.class));
        assertEquals(managerDTO, result);
    }

    @Test
    @DisplayName("Positive test. Get manager by status.")
    void testGetManagersStatus() {
        ManagerListDTO listDTO = new ManagerListDTO(managerDTOList);

        when(managerRepository.getAllByStatus(ManagerStatus.ACTIVE)).thenReturn(managerList);

        ManagerListDTO result = managerService.getManagersStatus();

        verify(managerRepository).getAllByStatus(ManagerStatus.ACTIVE);
        assertEquals(listDTO.getManagerDTOList(), result.getManagerDTOList());
    }

    @Test
    @DisplayName("Positive test. Create new manager.")
    void testCreate() {
        Manager expectedManager = EntityCreator.getManagerAfterDTO(uuid, createManagerDTO);
        ManagerDTO expectedManagerDTO = managerDTO;

        when(managerRepository.save(any(Manager.class))).thenReturn(expectedManager);

        ManagerDTO actualManagerDTO = managerService.create(createManagerDTO);
        assertNotNull(actualManagerDTO);
        compareEntityWithDto(expectedManagerDTO, actualManagerDTO);
        assertEquals(expectedManagerDTO.getCreatedAt(), actualManagerDTO.getCreatedAt());
        assertEquals(expectedManagerDTO.getUpdatedAt(), actualManagerDTO.getUpdatedAt());
    }

    @Test
    @DisplayName("Positive test. Delete manager by Id.")
    void testDeleteById() {
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.of(manager));

        managerService.deleteById(uuid);
        verify(managerRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Positive test. Edit manager by Id.")
    void testEditManagerById() {
        ManagerDTO expectedManagerDTO = managerDTO;

        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.of(manager));
        when(managerRepository.save(any())).thenReturn(manager);

        ManagerDTO actualManagerDTO = managerService.editManagerById(uuid, createManagerDTO);

        compareEntityWithDto(expectedManagerDTO, actualManagerDTO);
        assertNotNull(actualManagerDTO.getUpdatedAt());

        verify(managerRepository, times(1)).findManagerById(any(UUID.class));
        verify(managerRepository, times(1)).save(manager);
    }

    @Test
    @DisplayName("Negative test. Not found manager by Id.")
    public void editManagerById_shouldThrowExceptionWhenManagerNotFound() {
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ManagerNotFoundException.class, () -> managerService.editManagerById(uuid, createManagerDTO));
    }

    @Test
    @DisplayName("Positive test. Found all managers by status ACTIVE.")
    public void testGetAllManagersByStatus() {
        ManagerListDTO listDTO = new ManagerListDTO(managerDTOList);
        when(managerRepository.findByStatus(ManagerStatus.ACTIVE)).thenReturn(managerList);

        ManagerListDTO dto = managerService.getAllManagersByStatus(ManagerStatus.ACTIVE);

        verify(managerRepository).findByStatus(ManagerStatus.ACTIVE);
        assertEquals(dto.getManagerDTOList(), listDTO.getManagerDTOList());
    }

    @Test
    @DisplayName("Positive test. Get all managers.")
    void testGetAll(){
        ManagerListDTO listDTO = new ManagerListDTO(managerDTOList);

        when(managerRepository.findAll()).thenReturn(managerList);

        ManagerListDTO result = managerService.getAll();

        verify(managerRepository).findAll();
        assertEquals(listDTO.getManagerDTOList(), result.getManagerDTOList());
    }

    @Test
    void testGetManagerByNonExistingId(){
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ManagerNotFoundException.class, () -> managerService.getManagerById(uuid));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));
    }

    @Test
    public void testDeleteNonExistingManagerById(){
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ManagerNotFoundException.class, () -> managerService.deleteById(uuid));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));
    }

    private void compareEntityWithDto(ManagerDTO expectedManagerDTO, ManagerDTO actualManagerDTO){
        assertAll(
                () -> assertEquals(expectedManagerDTO.getId(), actualManagerDTO.getId()),
                () -> assertEquals(expectedManagerDTO.getFirstName(), actualManagerDTO.getFirstName()),
                () -> assertEquals(expectedManagerDTO.getLastName(), actualManagerDTO.getLastName()),
                () -> assertEquals(expectedManagerDTO.getStatus(), actualManagerDTO.getStatus())
        );
    }
}