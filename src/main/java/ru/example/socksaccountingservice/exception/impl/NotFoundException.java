package ru.example.socksaccountingservice.exception.impl;

import org.springframework.http.HttpStatus;

/**
 * Класс исключения, представляющий ошибку ненайденного элемента.
 */
public final class NotFoundException extends CustomException {

    /**
     * Конструктор для создания исключения с сообщением и причиной.
     *
     * @param message Сообщение об ошибке
     * @param cause   Причина ошибки
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Конструктор для создания исключения с сообщением, но без причины.
     *
     * @param message Сообщение об ошибке.
     */
    public NotFoundException(String message) {
        super(message, null);
    }

    /**
     * Конструктор для создания исключения без сообщения.
     *
     * @param cause причина ошибки
     */
    public NotFoundException(Throwable cause) {
        super(null, cause);
    }

    /**
     * Конструктор для создания исключения без сообщения и причины.
     */
    public NotFoundException() {
        super(null, null);
    }

    @Override
    public String description() {
        return "A Not Found exception has occurred.";
    }

    @Override
    public int httpStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
