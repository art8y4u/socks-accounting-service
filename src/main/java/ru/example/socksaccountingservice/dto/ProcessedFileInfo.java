package ru.example.socksaccountingservice.dto;

import lombok.Builder;

/**
 * Value object для передачи из сервиса в контроллер.
 *
 * @param processedRows кол-во обработанных строк
 * @param rowsSkipped   кол-во пропущенных строк
 */
@Builder
public record ProcessedFileInfo(
    int processedRows,
    int rowsSkipped
) {
}
