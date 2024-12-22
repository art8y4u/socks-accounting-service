package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Объект запроса на получение кол-ва на складе после фильтрации.
 */
@Getter
@SuperBuilder
@Schema(description = "Тело ответа на запрос кол-ва после фильтрации")
public class FilteredQuantityResponse extends SuccessResponse {

    /**
     * Кол-во на складе после применения фильтрации.
     */
    @JsonProperty("total_amount")
    @Schema(description = "Кол-во после фильтрации", example = "150",
        requiredMode = Schema.RequiredMode.REQUIRED)
    int amount;
}
