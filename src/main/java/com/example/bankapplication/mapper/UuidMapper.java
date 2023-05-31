package com.example.bankapplication.mapper;

import org.mapstruct.Mapper;

import java.util.UUID;

/**
 * The `UuidMapper` class is a mapper class that provides mapping methods
 * for converting between `UUID` objects and `String` values.
 * <p>
 * `@Mapper(componentModel = "spring")`: This annotation is from the MapStruct library and specifies
 * that this class should be treated as a mapper component.
 * The `componentModel="spring"` attribute indicates that Spring should manage the lifecycle of the mapper bean.
 * <p>
 * `String toStringValue(UUID uuid)`: This method converts a `UUID` object to a `String` value.
 * If the `uuid` parameter is not null, it returns the `toString()` representation of the `UUID`;
 * otherwise, it returns null.
 * <p>
 * `UUID toUuid(String uuid)`: This method converts a `String` value to a `UUID` object.
 * It uses the `UUID.fromString()` method to parse the `uuid` parameter and create a `UUID` object.
 */
@Mapper(componentModel = "spring")
public class UuidMapper {
    String toStringValue(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    UUID toUuid(String uuid) {
        return UUID.fromString(uuid);
    }
}