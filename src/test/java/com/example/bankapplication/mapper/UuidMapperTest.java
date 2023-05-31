package com.example.bankapplication.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test class for UuidMapper")
class UuidMapperTest {
    private final UuidMapper uuidMapper = new UuidMapper();

    private UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
    }

    @Test
    @DisplayName("Positive test. Convert uuid to String")
    void testToStringValue() {
        String uuidString = uuidMapper.toStringValue(uuid);

        assertNotNull(uuidString);
        assertEquals(uuid.toString(), uuidString);
    }

    @Test
    @DisplayName("Positive test. When correct String uuid convert to UUID")
    void testToUuid() {
        String uuidString = uuid.toString();
        UUID convertedUuid = uuidMapper.toUuid(uuidString);

        assertEquals(uuid, convertedUuid);
    }

    @Test
    @DisplayName("Negative test. When incorrect String uuid return IllegalArgumentException")
    void testGetIllegalArgumentExceptionForInvalidUuidString() {
        String uuid = "invalid-uuid";
        assertThrows(IllegalArgumentException.class, () -> uuidMapper.toUuid(uuid));
    }

    @Test
    @DisplayName("Negative test. When String uuid is null return NullPointerException")
    void testGetNullFromNullUuid() {
        assertThrows(NullPointerException.class, () -> uuidMapper.toUuid(null));
    }
}