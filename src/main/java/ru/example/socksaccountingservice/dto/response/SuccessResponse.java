package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Модель данных используемая для вывода сообщений об успешном выполнении запроса")
public class SuccessResponse {

    /**
     * Сообщение об успешной обработке.
     */
    @JsonProperty(value = "message")
    @Schema(description = "Детальное сообщение о результате",
        example = "Операция выполнена успешно")
    @Builder.Default
    protected String message = "Operation performed successfully.";
}


