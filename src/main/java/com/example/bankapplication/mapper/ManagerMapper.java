package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.entity.Manager;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UuidMapper.class)
public interface ManagerMapper {
    ManagerDTO toDTO(Manager manager);
    Manager toEntity(ManagerDTO managerDTO);
    List<ManagerDTO> managersToManagersDTO(List<Manager> managers);
}
