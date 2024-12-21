package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.example.socksaccountingservice.entity.enums.Color;

import java.util.UUID;

/**
 * Ответ на запрос изменения параметров.
 */
@Getter
@SuperBuilder
public class ChangeParametersResponse extends SuccessResponse {

    /**
     * Уникальный идентификатор измененной сущности.
     */
    @JsonProperty("id")
    private UUID id;

    /**
     * Цвет.
     */
    @JsonProperty("color")
    private Color color;

    /**
     * Процент содержания хлопка.
     */
    @JsonProperty("cotton_percentage")
    private int cottonPercentage;

    /**
     * Количество.
     */
    @JsonProperty("quantity")
    private int quantity;
}
