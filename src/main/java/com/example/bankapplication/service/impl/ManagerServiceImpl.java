package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerInfoDTO;
import com.example.bankapplication.dto.ManagerListDTO;
import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.entity.enums.ManagerStatus;
import com.example.bankapplication.mapper.ManagerMapper;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.service.ManagerService;
import com.example.bankapplication.service.exception.ErrorMessage;
import com.example.bankapplication.service.exception.ManagerNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The `ManagerServiceImpl` class is an implementation of the `ManagerService` interface.
 * It provides methods for performing various operations related to managers.
 *
 * @Service: This annotation is used to indicate that this class is a service component in the Spring framework.
 *
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to inject dependencies using constructor injection.
 *
 * @Slf4j: This annotation is from the Lombok library and generates a logger field for logging.
 *
 * @Transactional: This annotation is used in Spring to define transactional boundaries for methods or classes.
 * When applied to a method or class, it indicates that a transaction should be created for the annotated method
 * or all methods within the annotated class.
 * Transactional boundaries ensure that a group of operations are executed as a single atomic unit.
 * If an exception occurs during the execution of the annotated method or any method within the annotated class,
 * the transaction will be rolled back, and any changes made within the transaction will be undone.
 * By using the `@Transactional` annotation, we can manage transactions declaratively without having
 * to write explicit transaction management code. Spring takes care of creating, committing,
 * or rolling back transactions based on the annotated method's execution.
 * It is important to note that the `@Transactional` annotation should be applied to methods that modify data
 * or perform multiple database operations to ensure data integrity and consistency.
 *
 * ManagerRepository managerRepository: This field is used to access the manager data in the database.
 *
 * ManagerMapper managerMapper: This field is used to map manager entities to DTOs and vice versa.
 *
 * getManagerById(UUID id): This method retrieves a manager by their unique identifier (`id`).
 * It throws a `ManagerNotFoundException` if no manager with the specified `id` is found.
 *
 * getManagersStatus(): This method retrieves all managers with the status "ACTIVE".
 *
 * create(CreateManagerDTO dto): This method creates a new manager based on the provided DTO.
 *
 * deleteById(UUID id): This method deletes a manager by their unique identifier (`id`).
 * It throws a `ManagerNotFoundException` if no manager with the specified `id` is found.
 *
 * editManagerById(UUID id, CreateManagerDTO dto): This method updates a manager with the specified `id`
 * using the information provided in the DTO. It throws a `ManagerNotFoundException` if no manager with the specified `id` is found.
 *
 * getAllManagersByStatus(ManagerStatus status): This method retrieves all managers with the specified status.
 *
 * getAll(): This method retrieves all managers.
 *
 * The `ManagerServiceImpl` class implements the `ManagerService` interface,
 * which defines the contract for performing operations on managers.
 * By implementing this interface, the class provides the necessary business logic for manager-related operations.
 *
 * With the `ManagerServiceImpl` class, we can retrieve, create, update, and delete managers,
 * as well as get managers by status and get all managers.
 * It uses the `ManagerRepository` interface for data access and the `ManagerMapper` interface for entity-DTO mapping.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    private final ManagerMapper managerMapper;

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
        var manager = managerRepository.findManagerById(id);
        if(manager.isPresent())
            managerRepository.deleteById(id);
        else throw new ManagerNotFoundException(ErrorMessage.Manager_NOT_FOUND);
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
        manager.setStatus(ManagerStatus.valueOf(dto.getStatus()));
        manager.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        var result = managerRepository.save(manager);
        return managerMapper.toDTO(result);
    }

    @Override
    @Transactional
    public ManagerListDTO getAllManagersByStatus(ManagerStatus status) {
        log.info("Get all managers by status {}", status);
        return new ManagerListDTO(
                managerMapper.managersToManagersDTO(managerRepository.findByStatus(status)));
    }

    @Override
    @Transactional
    public ManagerListDTO getAll() {
        log.info("Get all managers");
        return new ManagerListDTO(managerMapper.managersToManagersDTO(managerRepository.findAll()));
    }

    @Override
    public List<ManagerInfoDTO> findAllManagersSortedByProductQuantity() {
        log.info("Sorted managers by product`s quantity");
        List<Manager> managerList = managerRepository.findAllManagersSortedByProductQuantity();
        List<ManagerInfoDTO> managerInfoDTOList = new ArrayList<>();
        if(managerList.isEmpty())
            throw new NullPointerException("The list of managers is empty.");

        for (var manager : managerList) {
            managerInfoDTOList.add(managerMapper.toInfoDTO(manager));
        }

        return managerInfoDTOList;
    }

}