package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientInfoDTO;
import com.example.bankapplication.dto.CreateClientDTO;
import com.example.bankapplication.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

/**
 * The `ClientMapper` interface is a mapper interface using the MapStruct library. It provides mapping methods
 * to convert between `Client` entities and `ClientDTO` data transfer objects.
 * <p>
 * `@Mapper(componentModel = "spring", imports = Timestamp.class)`: This annotation is from the MapStruct library
 * and specifies that this interface should be treated as a mapper component.
 * The `componentModel="spring"` attribute indicates that Spring should manage the lifecycle of the mapper bean.
 * The `imports = Timestamp.class` attribute specifies that the `Timestamp` class should be imported for usage
 * in mapping expressions.
 * <p>
 * `@Mapping(source = "client.manager.id", target = "managerId")`:
 * This annotation is used on the `toDTO` method to specify a mapping between the `managerId` property
 * of the `Client` entity and the `managerId` property of the `ClientDTO`. It maps the value from `client.manager.id`
 * to `managerId` during the conversion.
 * <p>
 * `ClientDTO toDTO(Client client)`:
 * This method maps a `Client` entity to a `ClientDTO`.
 * It uses the `@Mapping` annotation to define the mapping between properties.
 * <p>
 * `Client toEntity(ClientDTO clientDTO)`: This method maps a `ClientDTO` to a `Client` entity.
 * <p>
 * `List<ClientDTO> clientsToClientsDTO(List<Client> clients)`:
 * This method maps a list of `Client` entities to a list of `ClientDTO` objects.
 * <p>
 * `@Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")`:
 * This annotation is used on the `createToEntity` method to set the `createdAt` property of the `Client` entity.
 * It uses a mapping expression to create a new `Timestamp` object representing the current system time.
 * <p>
 * `Client createToEntity(CreateClientDTO dto)`:
 * This method maps a `CreateClientDTO` to a `Client` entity, including setting the `createdAt` property using
 * the mapping expression defined by the `@Mapping` annotation.
 */

@Mapper(componentModel = "spring", /*uses = UuidMapper.class,*/ imports = Timestamp.class)
public interface ClientMapper {
    @Mapping(source = "client.manager.id", target = "managerId")
    ClientDTO toDTO(Client client);

    Client toEntity(ClientDTO clientDTO);

    List<ClientDTO> clientsToClientsDTO(List<Client> clients);

    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Client createToEntity(CreateClientDTO dto);

    @Mapping(target = "balance", expression = "java(client.getAccounts().isEmpty() ? null : String.valueOf(client.getAccounts().get(0).getBalance()))")
    @Mapping(target = "currencyCode", expression = "java(client.getAccounts().isEmpty() ? null : String.valueOf(client.getAccounts().get(0).getCurrencyCode()))")
    ClientInfoDTO toClientDTO(Client client);
}
