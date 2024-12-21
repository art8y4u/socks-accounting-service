package ru.example.socksaccountingservice.controller;

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
    @PostMapping("/income")
    ResponseEntity<ChangeQuantityResponse> registerSockIncome(
        @Valid @RequestBody ChangeParametersRequest request);

    /**
     * Регистрация отпуска носков:
     * <p>
     * POST /api/socks/outcome
     * Параметры: цвет носков, процентное содержание хлопка, количество.
     * Уменьшает количество носков на складе, если их хватает.
     *
     * @param request объект запроса {@link ChangeParametersRequest}
     * @return ответ об успешном кол-ва {@link ChangeQuantityResponse}
     */
    @PostMapping("/outcome")
    ResponseEntity<ChangeQuantityResponse> registerSockOutcome(
        @Valid @RequestBody ChangeParametersRequest request);

    /**
     * PUT /api/socks/{id}.
     * Позволяет изменить параметры носков (цвет, процент хлопка, количество).
     *
     * @param id      уникальный идентификатор пары носков
     * @param request объект запроса {@link ChangeParametersRequest}
     * @return ответ с новыми характеристиками сущности {@link ChangeParametersResponse}
     */
    @PutMapping("/{id}")
    ResponseEntity<ChangeParametersResponse> changeSockPairParameters(
        @PathVariable("id") UUID id, @Valid @RequestBody ChangeParametersRequest request);

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
    @GetMapping
    ResponseEntity<FilteredQuantityResponse> getFilteredQuantity(
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
    @PostMapping("/batch")
    ProcessedFileResponse registerFileIncome(@RequestParam(name = "exel") MultipartFile file) throws IOException;
}
