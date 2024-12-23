package ru.example.socksaccountingservice.exception.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.example.socksaccountingservice.exception.serializer.CustomExceptionSerializer;

/**
 * Базовый класс кастомного исключения.
 */
@JsonSerialize(using = CustomExceptionSerializer.class)
public abstract sealed class CustomException extends RuntimeException implements CustomError
    permits NotFoundException, InvalidArgumentException, InternalException {

    /**
     * Конструктор класса.
     *
     * @param message сообщение об ошибке.
     * @param cause   причина ошибки.
     */
    CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Возвращает тип ошибки.
     * Преобразует в верхний регистр с uderscore-разделителями без Exception в названии.
     *
     * @return преобразованное строковое представление типа.
     */
    @Override
    public String type() {
        return String.join("_", this.getClass()
            .getSimpleName()
            .replace("Exception", "")
            .split("(?=\\p{Upper})")).toUpperCase();
    }
}
