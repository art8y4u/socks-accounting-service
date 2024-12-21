package ru.example.socksaccountingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import ru.example.socksaccountingservice.entity.enums.Color;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Объект запроса на изменение параметров.
 *
 * @param color            цвет носков
 * @param cottonPercentage содержание хлопка
 * @param quantity         количество
 */
@Builder
public record ChangeParametersRequest(
    @JsonProperty("color")
    @NotNull
    Color color,

    @JsonProperty("cotton_percentage")
    @Min(0)
    @Max(100)
    int cottonPercentage,

    @JsonProperty("quantity")
    @Positive
    int quantity
) {
}
