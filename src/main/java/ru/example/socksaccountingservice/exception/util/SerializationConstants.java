package ru.example.socksaccountingservice.exception.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс с константами для сериализации.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SerializationConstants {

    /**
     * Ключ для типа в JSON.
     */
    public static final String TYPE_KEY = "error_key";

    /**
     * Ключ для описания в JSON.
     */
    public static final String DESCRIPTION_KEY = "error_description";
}
