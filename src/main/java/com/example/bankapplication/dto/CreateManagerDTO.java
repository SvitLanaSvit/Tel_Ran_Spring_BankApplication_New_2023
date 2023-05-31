package com.example.bankapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

/**
 * The provided code snippet includes several annotations commonly used with Lombok library
 * to automatically generate boilerplate code for a class.
 *
 * @NoArgsConstructor: This annotation generates a no-argument constructor for the class.
 * @AllArgsConstructor: This annotation generates a constructor with parameters for all fields in the class.
 * @Getter: This annotation generates getter methods for all non-static fields in the class.
 * @Setter: This annotation generates setter methods for all non-final non-static fields in the class.
 * @FieldDefaults(level = AccessLevel.PRIVATE): This annotation sets the default access level
 * for fields in the class to private. It is typically used in combination with other annotations
 * from Lombok to specify the desired access level for fields.
 * <p>
 * * shape = JsonFormat.Shape.STRING: Specifies that the date should be serialized and deserialized as a String.
 * * pattern = "yyyy-MM-dd": Defines the pattern to be used for formatting the date as a String.
 * * In this case, the pattern indicates that the date should be formatted as "year-month-day" (e.g., "2023-05-18").
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateManagerDTO {
    String firstName;
    String lastName;
    String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Timestamp createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Timestamp updatedAt;
}