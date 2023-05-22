package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.dto.ManagerInfoDTO;
import com.example.bankapplication.entity.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

/**
 * The `ManagerMapper` interface is a mapper interface using the MapStruct library. It provides mapping methods
 * to convert between `Manager` entities and `ManagerDTO` data transfer objects.
 *
 * `@Mapper(componentModel = "spring", imports = Timestamp.class)`: This annotation is from the MapStruct library
 * and specifies that this interface should be treated as a mapper component.
 * The `componentModel="spring"` attribute indicates that Spring should manage the lifecycle of the mapper bean.
 * The `imports = Timestamp.class` attribute specifies that the `Timestamp` class should be imported for usage
 * in mapping expressions.
 *
 * `ManagerDTO toDTO(Manager manager)`:
 * This method maps a `Manager` entity to a `ManagerDTO`.
 *
 * `Manager toEntity(ManagerDTO managerDTO)`:
 * This method maps a `ManagerDTO` to a `Manager` entity.
 *
 * `List<ManagerDTO> managersToManagersDTO(List<Manager> managers)`:
 * This method maps a list of `Manager` entities to a list of `ManagerDTO` objects.
 *
 * `@Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")`:
 * This annotation is used on the `createToEntity` method to set the `createdAt` property of the `Manager` entity.
 * It uses a mapping expression to create a new `Timestamp` object representing the current system time.
 *
 * `Manager createToEntity(CreateManagerDTO managerDTO)`:
 * This method maps a `CreateManagerDTO` to a `Manager` entity, including setting the `createdAt` property using
 * the mapping expression defined by the `@Mapping` annotation.
 */
@Mapper(componentModel = "spring", /*uses = UuidMapper.class,*/ imports = Timestamp.class)
public interface ManagerMapper {
    ManagerDTO toDTO(Manager manager);
    Manager toEntity(ManagerDTO managerDTO);
    List<ManagerDTO> managersToManagersDTO(List<Manager> managers);
    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Manager createToEntity(CreateManagerDTO managerDTO);

    @Mapping(target = "productId", expression = "java(manager.getProducts().isEmpty() ? null : String.valueOf(manager.getProducts().get(0).getId()))")
    @Mapping(target = "name", expression = "java(manager.getProducts().isEmpty() ? null : String.valueOf(manager.getProducts().get(0).getName()))")
    @Mapping(target = "productLimit", expression = "java(manager.getProducts().isEmpty() ? null : String.valueOf(manager.getProducts().get(0).getProductLimit()))")
    ManagerInfoDTO toInfoDTO(Manager manager);
}
