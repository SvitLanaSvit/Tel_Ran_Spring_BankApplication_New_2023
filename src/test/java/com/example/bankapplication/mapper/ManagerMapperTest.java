package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerInfoDTO;
import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
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

    private UUID uuid;
    private Manager manager;
    private ManagerDTO managerDTO;
    private List<Manager> managerList;
    private List<ManagerDTO> managerDTOList;
    private CreateManagerDTO createManagerDTO;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        manager = EntityCreator.getManager(uuid);
        managerDTO = DTOCreator.getManagerDTO(uuid);
        managerList = new ArrayList<>(List.of(manager));
        managerDTOList = new ArrayList<>(List.of(managerDTO));
        createManagerDTO = DTOCreator.getManagerToCreateWithCreateDate();
    }

    @Test
    @DisplayName("Positive test. When we have correct entity then return correct ManagerDto")
    void testToDTO() {
        ManagerDTO managerDTO = managerMapper.toDTO(manager);
        compareEntityWithDto(manager, managerDTO);
    }

    @Test
    void testToDTONull() {
        ManagerDTO managerDTO = managerMapper.toDTO(null);
        assertNull(managerDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct ManagerDto then return correct entity")
    void testToEntity() {
        Manager manager = managerMapper.toEntity(managerDTO);
        compareEntityWithDto(manager, managerDTO);
    }

    @Test
    void testToEntityNull() {
        Manager manager = managerMapper.toEntity(null);
        assertNull(manager);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Manager then return correct list of ManagerDto")
    void testManagersToManagersDTO() {
        List<ManagerDTO> managerDTOList = managerMapper.managersToManagersDTO(managerList);
        compareManagerListWithListDto(managerList, managerDTOList);
    }

    @Test
    void testManagersToManagersDTONull() {
        List<ManagerDTO> managerDTOList = managerMapper.managersToManagersDTO(null);
        assertNull(managerDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        Manager manager = managerMapper.createToEntity(createManagerDTO);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String managerDate = date.format(manager.getCreatedAt());

        assertNull(createManagerDTO.getCreatedAt());
        assertNotNull(manager.getCreatedAt());
        assertEquals(managerDate, current);
    }

    @Test
    void testCreateToEntityNull() {
        Manager manager = managerMapper.createToEntity(null);
        assertNull(manager);
    }

    @Test
    void testToInfoDTO() {
        manager.setProducts(List.of(EntityCreator.getProduct(uuid)));
        ManagerInfoDTO managerInfoDTO = managerMapper.toInfoDTO(manager);
        assertEquals(manager.getId().toString(), managerInfoDTO.getId());
        assertEquals(manager.getFirstName(), managerInfoDTO.getFirstName());
        assertEquals(manager.getLastName(), managerInfoDTO.getLastName());
        assertEquals(manager.getProducts().get(0).getId().toString(), managerInfoDTO.getProductId());
        assertEquals(manager.getProducts().get(0).getName(), managerInfoDTO.getName());
        assertEquals(Integer.toString(manager.getProducts().get(0).getProductLimit()), managerInfoDTO.getProductLimit());
    }

    @Test
    void testToInfoDTONull() {
        ManagerInfoDTO managerInfoDTO = managerMapper.toInfoDTO(null);
        assertNull(managerInfoDTO);
    }

    private void compareEntityWithDto(Manager manager, ManagerDTO managerDTO) {
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