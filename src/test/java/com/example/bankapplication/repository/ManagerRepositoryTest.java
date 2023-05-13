package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.entity.enums.ManagerStatus;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for ManagerRepository")
class ManagerRepositoryTest {

    @Mock
    private ManagerRepository managerRepository;

    private Manager manager;
    private List<Manager> managerList;

    @BeforeEach
    void setUp(){
        manager = EntityCreator.getManager(UUID.randomUUID());
        managerList = new ArrayList<>(List.of(manager));
    }

    @Test
    @DisplayName("Positive test. Find manager by id.")
    void testFindManagerById() {
        when(managerRepository.findManagerById(manager.getId())).thenReturn(Optional.of(manager));
        Optional<Manager> foundManager = managerRepository.findManagerById(manager.getId());

        assertTrue(foundManager.isPresent());
        assertEquals(manager, foundManager.get());
        verify(managerRepository, times(1)).findManagerById(manager.getId());
    }

    @Test
    @DisplayName("Positive test. Find all managers by status.")
    void testGetAllByStatus() {
        when(managerRepository.getAllByStatus(ManagerStatus.ACTIVE)).thenReturn(managerList);
        List<Manager> foundManagers = managerRepository.getAllByStatus(ManagerStatus.ACTIVE);

        assertEquals(managerList.size(), foundManagers.size());
        verify(managerRepository, times(1)).getAllByStatus(ManagerStatus.ACTIVE);
    }

    @Test
    @DisplayName("Positive test. Find all clients by ACTIVE status.")
    void testFindByStatus() {
        when(managerRepository.findByStatus(ManagerStatus.ACTIVE)).thenReturn(managerList);
        List<Manager> foundManagers = managerRepository.findByStatus(ManagerStatus.ACTIVE);

        assertEquals(managerList.size(), foundManagers.size());
        verify(managerRepository, times(1)).findByStatus(ManagerStatus.ACTIVE);
    }

    @Test
    @DisplayName("Positive test. Find all clients.")
    void testFindAll() {
        when(managerRepository.findAll()).thenReturn(managerList);
        List<Manager> foundManagers = managerRepository.findAll();

        assertEquals(managerList.size(), foundManagers.size());
        verify(managerRepository, times(1)).findAll();
    }
}