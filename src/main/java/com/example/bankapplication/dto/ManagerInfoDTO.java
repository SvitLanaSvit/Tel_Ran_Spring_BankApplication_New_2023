package com.example.bankapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * The provided code snippet includes several annotations commonly used with Lombok library
 * to automatically generate boilerplate code for a class.
 *
 * @NoArgsConstructor: This annotation generates a no-argument constructor for the class.
 *
 * @AllArgsConstructor: This annotation generates a constructor with parameters for all fields in the class.
 *
 * @Getter: This annotation generates getter methods for all non-static fields in the class.
 *
 * @Setter: This annotation generates setter methods for all non-final non-static fields in the class.
 *
 * @FieldDefaults(level = AccessLevel.PRIVATE): This annotation sets the default access level
 * for fields in the class to private. It is typically used in combination with other annotations
 * from Lombok to specify the desired access level for fields.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerInfoDTO {
    String id;
    String firstName;
    String lastName;
    String status;
    String productId;
    String name;
    String productLimit;
}
