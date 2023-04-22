package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.entity.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

@Mapper(componentModel = "spring", /*uses = UuidMapper.class,*/ imports = Timestamp.class)
public interface ManagerMapper {
    ManagerDTO toDTO(Manager manager);
    Manager toEntity(ManagerDTO managerDTO);
    List<ManagerDTO> managersToManagersDTO(List<Manager> managers);
    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Manager createToEntity(CreateManagerDTO managerDTO);
}
