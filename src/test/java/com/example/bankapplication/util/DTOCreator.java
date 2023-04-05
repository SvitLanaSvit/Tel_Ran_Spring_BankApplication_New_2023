package com.example.bankapplication.util;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.entity.enums.ManagerStatus;

import java.sql.Timestamp;
import java.util.UUID;

public class DTOCreator {
    public static ManagerDTO getManagerDTO(UUID id){
        return new ManagerDTO(
                id.toString(),
                "John",
                "Doe",
                "ACTIVE",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00")
        );
    }

    public static CreateManagerDTO getManagerToCreate(){
        return new CreateManagerDTO(
                "John",
                "Doe",
                "ACTIVE",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00")
        );
    }
}
