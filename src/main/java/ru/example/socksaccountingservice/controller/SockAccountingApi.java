package ru.example.socksaccountingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.example.socksaccountingservice.dto.ProcessedFileInfo;
import ru.example.socksaccountingservice.dto.request.ChangeParametersRequest;
import ru.example.socksaccountingservice.dto.request.FilteredQuantityRequest;
import ru.example.socksaccountingservice.dto.response.ChangeParametersResponse;
import ru.example.socksaccountingservice.dto.response.ChangeQuantityResponse;
import ru.example.socksaccountingservice.dto.response.FilteredQuantityResponse;
import ru.example.socksaccountingservice.dto.response.ProcessedFileResponse;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

/**
 * API, предоставляющий операции по учету носков.
 */
@RequestMapping("/socks")
@Tag(name = "SockAccountingApi",
    description = "API контроллера для обработки запросов, связанных с учетом носков")
public interface SockAccountingApi {

    /**
     * Регистрация прихода носков:
     * <p>
     * POST /api/socks/income
     * Параметры: цвет носков, процентное содержание хлопка, количество.
     * Увеличивает количество носков на складе.
     *
     * @param request объект запроса {@link ChangeParametersRequest}
     * @return ответ об успешном изменении кол-ва {@link ChangeQuantityResponse}
     */
    @Operation(operationId = "registerSockIncome",
        summary = "Регистрация прихода носков")
    @ApiResponse(responseCode = "200", description = "Ответ об успешной регистрации прихода",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ChangeQuantityResponse.class)))
    @PostMapping("/income")
    ResponseEntity<ChangeQuantityResponse> registerSockIncome(
        @Parameter(description = "Модель запроса с информацией об изменении кол-ва")
        @Valid @RequestBody ChangeParametersRequest request);

    /**
     * Регистрация отпуска носков:
     * <p>
     * POST /api/socks/outcome
     * Параметры: цвет носков, процентное содержание хлопка, количество.
     * Уменьшает количество носков на складе, если их хватает.
     *
     * @param request объект запроса {@link ChangeParametersRequest}
     * @return ответ об успешном изменении кол-ва {@link ChangeQuantityResponse}
     */
    @Operation(operationId = "registerSockOutcome",
        summary = "Регистрация отпуска носков")
    @ApiResponse(responseCode = "200", description = "Ответ об успешной регистрации отпуска",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ChangeQuantityResponse.class)))
    @PostMapping("/outcome")
    ResponseEntity<ChangeQuantityResponse> registerSockOutcome(
        @Parameter(description = "Модель запроса с информацией об изменении кол-ва")
        @Valid @RequestBody ChangeParametersRequest request);

    /**
     * PUT /api/socks/{id}.
     * Позволяет изменить параметры носков (цвет, процент хлопка, количество).
     *
     * @param id      уникальный идентификатор пары носков
     * @param request объект запроса {@link ChangeParametersRequest}
     * @return ответ с новыми характеристиками сущности {@link ChangeParametersResponse}
     */
    @Operation(operationId = "changeSockPairParameters",
        summary = "Изменение параметров существующих носков")
    @ApiResponse(responseCode = "200", description = "Ответ об успешном изменении параметров",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ChangeParametersResponse.class)))
    @PutMapping("/{id}")
    ResponseEntity<ChangeParametersResponse> changeSockPairParameters(
        @Parameter(description = "Уникальный идентификатор целевой сущности")
        @PathVariable("id")
        UUID id,
        @Parameter(description = "Модель запроса с информацией об изменении параметров")
        @Valid @RequestBody
        ChangeParametersRequest request);

    /**
     * Получение общего количества носков с фильтрацией:
     * <p>
     * GET /api/socks
     * Фильтры:
     * Цвет носков.
     * Оператор сравнения (moreThan, lessThan, equal).
     * Процент содержания хлопка.
     * Возвращает количество носков, соответствующих критериям.
     *
     * @param request запрос с аттрибутами на фильтрацию {@link FilteredQuantityRequest}
     * @return ответ с доступным кол-вом после фильтрации {@link FilteredQuantityResponse}
     */
    @Operation(operationId = "getFilteredQuantity",
        summary = "Получение общего кол-ва с фильтрацией")
    @ApiResponse(responseCode = "200", description = "Ответ об успешном получении общего кол-ва после фильтрации",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = FilteredQuantityResponse.class)))
    @GetMapping
    ResponseEntity<FilteredQuantityResponse> getFilteredQuantity(
        @Parameter(description = "Модель запроса с параметрами фильтрации")
        @Valid @ModelAttribute FilteredQuantityRequest request);

    /**
     * Загрузка партий носков из Excel файла:
     * <p>
     * POST /api/socks/batch
     * Принимает Excel файл с партиями носков, содержащими цвет,
     * процентное содержание хлопка и количество.
     *
     * @param file файл, содержащий данные партий
     * @return объект статистики обработки {@link ProcessedFileInfo}
     * @throws IOException ошибка при чтении и обработке файла
     */
    @Operation(operationId = "registerFileIncome",
        summary = "Регистрация прихода из Excel файла")
    @ApiResponse(responseCode = "200", description = "Успешный ответ со статистикой обработки",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProcessedFileResponse.class)))
    @PostMapping("/batch")
    ProcessedFileResponse registerFileIncome(
        @Parameter(description = "Файл с данными прихода")
        @RequestParam(name = "exel") MultipartFile file) throws IOException;
}
