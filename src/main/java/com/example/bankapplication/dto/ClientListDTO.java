package com.example.bankapplication.dto;

import lombok.Value;
import java.util.List;

@Value
public class ClientListDTO {
    List<ClientDTO> clientDTOList;
}
