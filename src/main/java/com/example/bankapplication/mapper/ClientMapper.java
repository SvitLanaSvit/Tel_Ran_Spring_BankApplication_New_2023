package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UuidMapper.class)
public interface ClientMapper {
    @Mapping(source = "client.manager.id", target = "managerId")
    ClientDTO toDTO(Client client);
    Client toEntity(ClientDTO clientDTO);
    List<ClientDTO> clientsToClientsDTO(List<Client> clients);
}
