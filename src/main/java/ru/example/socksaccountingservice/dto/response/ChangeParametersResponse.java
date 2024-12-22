package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.example.socksaccountingservice.entity.enums.Color;

import java.util.UUID;

/**
 * Ответ на запрос изменения параметров.
 */
@Getter
@SuperBuilder
@Schema(description = "Тело ответа на запрос изменения параметров")
public class ChangeParametersResponse extends SuccessResponse {

    /**
     * Уникальный идентификатор измененной сущности.
     */
    @JsonProperty("id")
    @Schema(description = "Уникальный идентификатор",
        example = "cf3146f2-49a6-4e85-9aa9-12036b125a00",
        requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;

    /**
     * Цвет.
     */
    @JsonProperty("color")
    @Schema(description = "Цвет", example = "BLACK",
        requiredMode = Schema.RequiredMode.REQUIRED)
    private Color color;

    /**
     * Процент содержания хлопка.
     */
    @JsonProperty("cotton_percentage")
    @Schema(description = "Процентное содержание хлопка", example = "80",
        requiredMode = Schema.RequiredMode.REQUIRED)
    private int cottonPercentage;

    /**
     * Количество.
     */
    @JsonProperty("quantity")
    @Schema(description = "Количество", example = "75",
        requiredMode = Schema.RequiredMode.REQUIRED)
    private int quantity;
}
