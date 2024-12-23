package ru.example.socksaccountingservice.exception.impl;

import org.springframework.http.HttpStatus;

/**
 * Класс исключения, представляющий ошибку неправильного запроса/аргумента.
 */
public final class InvalidArgumentException extends CustomException {

    /**
     * Конструктор для создания исключения с сообщением и причиной.
     *
     * @param message Сообщение об ошибке
     * @param cause   Причина ошибки
     */
    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Конструктор для создания исключения без причины.
     *
     * @param message сообщение об ошибке
     */
    public InvalidArgumentException(String message) {
        super(message, null);
    }

    /**
     * Конструктор для создания исключения без сообщения.
     *
     * @param cause причина ошибки
     */
    public InvalidArgumentException(Throwable cause) {
        super(null, cause);
    }

    /**
     * Конструктор для создания исключения без сообщения и причины.
     */
    public InvalidArgumentException() {
        super(null, null);
    }

    @Override
    public String description() {
        return "Invalid request exception has occurred.";
    }

    @Override
    public int httpStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
