package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Positive;

/**
 * Объект ответа, результата обработки файла.
 */
@Getter
@SuperBuilder
public class ProcessedFileResponse extends SuccessResponse {

    /**
     * Кол-во обработанных строк таблицы.
     */
    @JsonProperty("processed")
    @Positive
    private int processed;

    /**
     * Кол-во пропущенных строк при обработке.
     */
    @JsonProperty("skipped")
    @Positive
    private int skipped;
}

