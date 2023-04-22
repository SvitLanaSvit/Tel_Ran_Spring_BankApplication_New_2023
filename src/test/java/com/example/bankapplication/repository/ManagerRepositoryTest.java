package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.entity.enums.ManagerStatus;
import com.example.bankapplication.util.EntityCreator;
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

    @Test
    @DisplayName("Positive test. Find manager by id.")
    void testFindManagerById() {
        Manager manager = EntityCreator.getManager(UUID.randomUUID());

        when(managerRepository.findManagerById(manager.getId())).thenReturn(Optional.of(manager));
        Optional<Manager> foundManager = managerRepository.findManagerById(manager.getId());

        assertTrue(foundManager.isPresent());
        assertEquals(manager, foundManager.get());
        verify(managerRepository, times(1)).findManagerById(manager.getId());
    }

    @Test
    @DisplayName("Positive test. Find all managers by status.")
    void testGetAllByStatus() {
        List<Manager> managers = new ArrayList<>(List.of(
                EntityCreator.getManager(UUID.randomUUID())
        ));

        when(managerRepository.getAllByStatus(ManagerStatus.ACTIVE)).thenReturn(managers);
        List<Manager> foundManagers = managerRepository.getAllByStatus(ManagerStatus.ACTIVE);

        assertEquals(managers.size(), foundManagers.size());
        verify(managerRepository, times(1)).getAllByStatus(ManagerStatus.ACTIVE);
    }

    @Test
    @DisplayName("Positive test. Find all clients by ACTIVE status.")
    void testFindByStatus() {
        List<Manager> managers = new ArrayList<>(List.of(
                EntityCreator.getManager(UUID.randomUUID())
        ));

        when(managerRepository.findByStatus(ManagerStatus.ACTIVE)).thenReturn(managers);
        List<Manager> foundManagers = managerRepository.findByStatus(ManagerStatus.ACTIVE);

        assertEquals(managers.size(), foundManagers.size());
        verify(managerRepository, times(1)).findByStatus(ManagerStatus.ACTIVE);
    }

    @Test
    @DisplayName("Positive test. Find all clients.")
    void testFindAll() {
        List<Manager> managers = new ArrayList<>(List.of(
                EntityCreator.getManager(UUID.randomUUID())
        ));

        when(managerRepository.findAll()).thenReturn(managers);
        List<Manager> foundManagers = managerRepository.findAll();

        assertEquals(managers.size(), foundManagers.size());
        verify(managerRepository, times(1)).findAll();
    }
}