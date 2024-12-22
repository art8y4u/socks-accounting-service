package ru.example.socksaccountingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Тело запроса на получения кол-ва носков после фильтрации")
public record FilteredQuantityRequest(
    @JsonProperty("color")
    @NotNull
    @Schema(description = "Цвет носков для фильтрации", example = "BLACK")
    Color color,

    @JsonProperty("operator")
    @NotNull
    @Schema(description = "Оператор сравнения для фильтрации", example = "GREATER_THAN")
    ComparisonOperator operator,

    @JsonProperty("cotton")
    @NotNull
    @Min(0)
    @Max(100)
    @Schema(description = "Процент содержания хлопка", example = "50")
    Integer cotton
) {
}
