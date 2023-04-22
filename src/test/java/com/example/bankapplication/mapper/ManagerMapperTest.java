package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Test class for ManagerMapper")
class ManagerMapperTest {

    private final ManagerMapper managerMapper = new ManagerMapperImpl();

    @Test
    @DisplayName("Positive test. When we have correct entity then return correct ManagerDto")
    void testToDTO() {
        UUID id = UUID.randomUUID();
        Manager manager = EntityCreator.getManager(id);
        System.out.println(manager.getId());
        ManagerDTO managerDTO = managerMapper.toDTO(manager);
        compareEntityWithDto(manager, managerDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct ManagerDto then return correct entity")
    void testToEntity() {
        UUID id = UUID.randomUUID();
        ManagerDTO dto = DTOCreator.getManagerDTO(id);
        System.out.println(dto.getId());
        Manager manager = managerMapper.toEntity(dto);
        compareEntityWithDto(manager, dto);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Manager then return correct list of ManagerDto")
    void testManagersToManagersDTO() {
        UUID id = UUID.randomUUID();
        List<Manager> managerList = new ArrayList<>();
        managerList.add(EntityCreator.getManager(id));

        List<ManagerDTO> managerDTOList = managerMapper.managersToManagersDTO(managerList);
        compareManagerListWithListDto(managerList, managerDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        CreateManagerDTO dto = DTOCreator.getManagerToCreateWithCreateDate();
        Manager manager = managerMapper.createToEntity(dto);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String managerDate = date.format(manager.getCreatedAt());

        assertNull(dto.getCreatedAt());
        assertNotNull(manager.getCreatedAt());
        assertEquals(managerDate, current);
    }

    private void compareEntityWithDto(Manager manager, ManagerDTO managerDTO){
        assertAll(
                () -> assertEquals(manager.getId().toString(), managerDTO.getId()),
                () -> assertEquals(manager.getFirstName(), managerDTO.getFirstName()),
                () -> assertEquals(manager.getLastName(), managerDTO.getLastName()),
                () -> assertEquals(manager.getStatus().toString(), managerDTO.getStatus()),
                () -> assertEquals(manager.getCreatedAt(), managerDTO.getCreatedAt()),
                () -> assertEquals(manager.getUpdatedAt(), managerDTO.getUpdatedAt())
        );
    }

    private void compareManagerListWithListDto(List<Manager> managerList, List<ManagerDTO> managerDTOList) {
        assertEquals(managerList.size(), managerDTOList.size());
        for (int i = 0; i < managerList.size(); i++) {
            compareEntityWithDto(managerList.get(i), managerDTOList.get(i));
        }
    }
}