package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Объект ответа на изменения кол-ва.
 */
@Getter
@SuperBuilder
@Schema(description = "Тело ответа на изменение количества")
public class ChangeQuantityResponse extends SuccessResponse {

    /**
     * Уникальный идентификатор сущности.
     */
    @JsonProperty("socks_id")
    @Schema(description = "Уникальный идентификатор",
        example = "cf3146f2-49a6-4e85-9aa9-12036b125a00",
        requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;

    /**
     * Новое кол-во после изменения.
     */
    @JsonProperty("new_quantity")
    @Schema(description = "Количество после изменения", example = "100",
        requiredMode = Schema.RequiredMode.REQUIRED)
    private int newQuantity;
}
