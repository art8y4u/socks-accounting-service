package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Объект запроса на изменения кол-ва.
 */
@Getter
@SuperBuilder
public class ChangeQuantityResponse extends SuccessResponse {

    /**
     * Уникальный идентификатор сущности.
     */
    @JsonProperty("socks_id")
    private UUID id;

    /**
     * Новое кол-во после изменения.
     */
    @JsonProperty("new_quantity")
    private int newQuantity;
}
