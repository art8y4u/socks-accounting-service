package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Объект запроса на получение кол-ва на складе после фильтрации.
 */
@Getter
@SuperBuilder
public class FilteredQuantityResponse extends SuccessResponse {

    /**
     * Кол-во на складе после применения фильтрации.
     */
    @JsonProperty("total_amount")
    int amount;
}
