package com.example.bankapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerInfoDTO {
    String managerId;
    String firstName;
    String lastName;
    String status;
    String productId;
    String name;
    String productLimit;
}
