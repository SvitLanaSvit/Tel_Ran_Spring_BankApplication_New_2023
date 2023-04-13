package com.example.bankapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientInfoDTO {
    String UUID;
    String status;
    String taxCode;
    String firstName;
    String lastName;
    String email;
    String address;
    String phone;
    String balance;
    String currencyCode;
}
