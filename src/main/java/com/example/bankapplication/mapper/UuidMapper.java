package com.example.bankapplication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public class UuidMapper {
    String toStringValue(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }
    UUID toUuid(String uuid) {
        return UUID.fromString(uuid);
    }
}
