package ru.example.socksaccountingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Тело запроса для изменения параметров")
public record ChangeParametersRequest(
    @JsonProperty("color")
    @NotNull
    @Schema(description = "Цвет носков", example = "BLACK")
    Color color,

    @JsonProperty("cotton_percentage")
    @Min(0)
    @Max(100)
    @Schema(description = "Содержание хлопка в процентах", example = "75")
    int cottonPercentage,

    @JsonProperty("quantity")
    @Positive
    @Schema(description = "Количество носков", example = "10")
    int quantity
) {
}
