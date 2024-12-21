package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Базовый объект ответа, содержащий успешное сообщение обработки.
 */
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class SuccessResponse {

    /**
     * Сообщение об успешной обработке.
     */
    @JsonProperty(value = "message")
    @Builder.Default
    protected String message = "Operation performed successfully.";
}


