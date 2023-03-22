package com.example.bankapplication.mapper;

import org.mapstruct.Mapper;
import java.util.UUID;

@Mapper(componentModel = "spring")
public class UuidMapper {
    String toString(UUID uuid) {
        return uuid.toString();
    }
    UUID fromString(String uuid) {
        return UUID.fromString(uuid);
    }
}
