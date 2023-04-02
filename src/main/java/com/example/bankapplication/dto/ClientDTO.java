package com.example.bankapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import java.sql.Timestamp;

@Value
public class ClientDTO {
    String id;
    String status;
    String taxCode;
    String firstName;
    String lastName;
    String email;
    String address;
    String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Timestamp updatedAt;

    //ManagerDTO manager;
    String managerId;
}
