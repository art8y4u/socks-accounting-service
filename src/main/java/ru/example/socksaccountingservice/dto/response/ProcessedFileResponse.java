package ru.example.socksaccountingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Positive;

/**
 * Объект ответа, результата обработки файла.
 */
@Getter
@SuperBuilder
@Schema(description = "Тело ответа после обработки файла")
public class ProcessedFileResponse extends SuccessResponse {

    /**
     * Кол-во обработанных строк таблицы.
     */
    @JsonProperty("processed")
    @Positive
    @Schema(
        description = "Количество успешно обработанных строк",
        example = "15",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int processed;

    /**
     * Кол-во пропущенных строк при обработке.
     */
    @JsonProperty("skipped")
    @Positive
    @Schema(
        description = "Количество пропущенных строк при обработке",
        example = "5",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int skipped;
}

