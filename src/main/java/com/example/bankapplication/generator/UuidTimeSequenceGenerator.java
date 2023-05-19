package com.example.bankapplication.generator;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import java.io.Serializable;
import java.util.UUID;

/**
 * The `UuidTimeSequenceGenerator` class a custom implementation of the Hibernate `IdentifierGenerator` interface.
 * It generates UUIDs based on a combination of the current system time and a sequence value obtained from a database.
 *
 * - `@RequiredArgsConstructor`: This annotation is from the Lombok library and generates a constructor
 * with required arguments for the class.
 *
 * - `private static final String NEXT_VAL_QUERY`: This string represents the SQL query used to retrieve
 * the next sequence value from the database.
 *
 * - `private final JdbcTemplate jdbcTemplate`: This field is an instance of `JdbcTemplate`,
 * which is a class provided by Spring JDBC for executing SQL statements. JdbcTemplate is a class provided
 * by the Spring Framework that simplifies the process of interacting with relational
 * databases using JDBC (Java Database Connectivity). It provides a higher-level abstraction over JDBC APIs
 * and eliminates much of the repetitive boilerplate code associated with database operations.
 *
 * - `generate()`: This method is the implementation of the `generate` method from the `IdentifierGenerator` interface.
 * It generates a UUID by combining the current system time (in milliseconds) and
 * a sequence value retrieved from the database. The generated UUID is returned as a `Serializable` object.
 *
 * - `getSequenceValue()`: This method executes the SQL query to retrieve the next sequence value from
 * the database using the `JdbcTemplate`.
 *
 * - `concatInHexFormat()`: This method concatenates the hexadecimal representation of the current system time
 * and sequence value into a `char` array.
 *
 * - `formatUuidToString()`: This method formats the `char` array representation of the UUID by adding dashes
 * at specific positions and replacing any `0` characters with `'0'`.
 *
 * - `isDashPosition()`: This method checks if a given position in the `char` array corresponds to a dash position
 * in the UUID format.
 *
 * Overall, the `UuidTimeSequenceGenerator` class provides a custom implementation of generating UUIDs based
 * on a combination of time and a sequence value obtained from a database.
 */
@RequiredArgsConstructor
public class UuidTimeSequenceGenerator implements IdentifierGenerator {

    private static final String NEXT_VAL_QUERY = "SELECT nextval('seq_for_uuid_generator');";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        long currTimeMillis = System.currentTimeMillis();
        long sequenceValue = getSequenceValue();

        char[] uuidRaw = concatInHexFormat(currTimeMillis, sequenceValue);

        return UUID.fromString(formatUuidToString(uuidRaw));
    }

    private Long getSequenceValue() {
        return jdbcTemplate.queryForObject(NEXT_VAL_QUERY, (rs, rowNum) -> rs.getLong(1));
    }

    private char[] concatInHexFormat(long currTimeMillis, long sequenceValue) {
        char[] millisHex = Long.toHexString(currTimeMillis).toCharArray();
        char[] seqHex = Long.toHexString(sequenceValue).toCharArray();
        char[] concatenated = new char[36];

        System.arraycopy(millisHex, 0, concatenated, 0, millisHex.length);
        System.arraycopy(seqHex, 0, concatenated, 16, seqHex.length);

        return concatenated;
    }

    private String formatUuidToString(char[] uuid){
        for (int i = 0; i < uuid.length; i++) {
            if(isDashPosition(i)){
                System.arraycopy(uuid, i, uuid, i + 1, uuid.length - 1 - i);
                uuid[i] = '-';
            }else if(uuid[i] == 0){
                uuid[i] = '0';
            }
        }
        return new String(uuid);
    }

    private boolean isDashPosition(int pos){
        return pos == 8 || pos == 13 || pos == 18 || pos == 23;
    }
}