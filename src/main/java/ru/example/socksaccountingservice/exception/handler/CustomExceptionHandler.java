package ru.example.socksaccountingservice.exception.handler;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.example.socksaccountingservice.exception.impl.CustomException;
import ru.example.socksaccountingservice.exception.impl.InternalException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Глобальный обработчик исключений для REST-контроллеров.
 * <p>
 * Этот класс обеспечивает централизованную обработку исключений для всех конечных точек,
 * используя {@link RestControllerAdvice} из Spring.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    /**
     * Обрабатывает все необработанные исключения в приложении.
     * <p>
     * Для исключений, являющихся экземплярами {@link CustomException}, делегирует обработку.
     * Для других исключений они оборачиваются в {@link InternalException}.
     * </p>
     *
     * @param throwable необработанное исключение, которое нужно обработать.
     * @return {@link ResponseEntity}, содержащий структурированный JSON-ответ с соответствующим HTTP-кодом статуса.
     */
    @ApiResponse(responseCode = "400",
        description = """
            Содержит неверные параметры,
            отсутствующие обязательные поля или неверный формат данных""",
        content = @Content(mediaType = APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404",
        description =
            "Данные не были найдены.",
        content = @Content(mediaType = APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500",
        description =
            "Внутренняя ошибка сервера.",
        content = @Content(mediaType = APPLICATION_JSON_VALUE))
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<CustomException> handleThrowable(Throwable throwable) {

        if (throwable instanceof CustomException er) {
            return handleCustomError(er);
        }

        return handleCustomError(new InternalException(throwable));
    }

    /**
     * Обрабатывает исключения типа {@link CustomException}.
     * <p>
     * Формирует JSON-ответ с кодом статуса, сообщением об ошибке.
     * </p>
     *
     * @param exception исключение для обработки.
     * @return структурированный JSON-ответ об ошибке.
     */
    public ResponseEntity<CustomException> handleCustomError(CustomException exception) {

        return ResponseEntity.status(exception.httpStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body(exception);
    }
}
