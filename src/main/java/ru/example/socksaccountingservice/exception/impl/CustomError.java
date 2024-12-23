package ru.example.socksaccountingservice.exception.impl;

/**
 * Интерфейс кастомного исключения.
 */
public interface CustomError {

    /**
     * Тип ошибки.
     *
     * @return строковое представление ошибки
     */
    String type();

    /**
     * Описание ошибки.
     *
     * @return строковое представление описания
     */
    String description();

    /**
     * Http-статус код ошибки.
     *
     * @return численное представление http статус-кода ошибки
     */
    int httpStatusCode();
}
