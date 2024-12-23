package ru.example.socksaccountingservice.exception.impl;

import org.springframework.http.HttpStatus;

/**
 * Класс исключения, представляющий внутреннюю ошибку.
 */
public final class InternalException extends CustomException {

    /**
     * Конструктор для создания исключения с сообщением и причиной.
     *
     * @param message Сообщение об ошибке
     * @param cause   Причина ошибки
     */
    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Конструктор для создания исключения без сообщения.
     *
     * @param cause причина ошибки
     */
    public InternalException(Throwable cause) {
        super(null, cause);
    }

    /**
     * Конструктор для создания исключения без причины.
     *
     * @param message сообщение об ошибке
     */
    public InternalException(String message) {
        super(message, null);
    }

    /**
     * Конструктор для создания исключения без сообщения и причины.
     */
    public InternalException() {
        super(null, null);
    }

    @Override
    public String description() {
        return "An Internal Exception has occurred.";
    }

    @Override
    public int httpStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
