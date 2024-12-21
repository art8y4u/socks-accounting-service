package ru.example.socksaccountingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import ru.example.socksaccountingservice.entity.enums.Color;
import ru.example.socksaccountingservice.entity.enums.ComparisonOperator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Объект запроса на получение количества носков на складе после фильтрации.
 *
 * @param color    цвет
 * @param operator оператор сравнения {@link ComparisonOperator}
 * @param cotton   содержание хлопка
 */
@Builder
public record FilteredQuantityRequest(
    @JsonProperty("color")
    @NotNull
    Color color,

    @JsonProperty("operator")
    @NotNull
    ComparisonOperator operator,

    @JsonProperty("cotton")
    @NotNull
    @Min(0)
    @Max(100)
    Integer cotton
) {
}
