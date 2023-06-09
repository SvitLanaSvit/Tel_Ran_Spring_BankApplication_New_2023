package com.example.bankapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.sql.Timestamp;

/**
 * The @Value annotation is provided by Spring Framework and is used to inject values into fields
 * or constructor parameters. It is commonly used for injecting configuration properties or literal values.
 * <p>
 * The @JsonFormat annotation is provided by the Jackson library and is used to define the formatting of dates
 * or other values during JSON serialization and deserialization.
 * <p>
 * In the provided code snippet, the @JsonFormat annotation is used with the following parameters:
 * <p>
 * shape = JsonFormat.Shape.STRING: Specifies that the date should be serialized and deserialized as a String.
 * pattern = "yyyy-MM-dd": Defines the pattern to be used for formatting the date as a String.
 * In this case, the pattern indicates that the date should be formatted as "year-month-day" (e.g., "2023-05-18").
 */
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
