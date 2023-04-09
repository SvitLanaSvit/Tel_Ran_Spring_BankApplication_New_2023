package com.example.bankapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductListDTO {
    List<ProductDTO> productDTOList;
}
