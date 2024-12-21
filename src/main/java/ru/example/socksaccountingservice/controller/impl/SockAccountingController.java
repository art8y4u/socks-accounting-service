package ru.example.socksaccountingservice.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.example.socksaccountingservice.controller.SockAccountingApi;
import ru.example.socksaccountingservice.dto.ProcessedFileInfo;
import ru.example.socksaccountingservice.dto.request.ChangeParametersRequest;
import ru.example.socksaccountingservice.dto.request.FilteredQuantityRequest;
import ru.example.socksaccountingservice.dto.response.ChangeParametersResponse;
import ru.example.socksaccountingservice.dto.response.ChangeQuantityResponse;
import ru.example.socksaccountingservice.dto.response.FilteredQuantityResponse;
import ru.example.socksaccountingservice.dto.response.ProcessedFileResponse;
import ru.example.socksaccountingservice.entity.SockPair;
import ru.example.socksaccountingservice.mapper.SockAccountingMapper;
import ru.example.socksaccountingservice.service.SockAccountingService;

import java.io.IOException;
import java.util.UUID;

/**
 * Контроллер, реализующий интерфейс API операций по учету носков.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SockAccountingController implements SockAccountingApi {

    /**
     * Сервис для работы с сущностями {@link SockAccountingService}.
     */
    private final SockAccountingService service;

    /**
     * Преобразователь.
     */
    private final SockAccountingMapper mapper;

    @Override
    public ResponseEntity<ChangeQuantityResponse> registerSockIncome(ChangeParametersRequest request) {
        log.info("registerSockIncome() - start. request: {}", request);

        final SockPair sockPair = service.registerSockIncome(request.color(),
            request.cottonPercentage(), request.quantity());

        log.info("registerSockIncome() - return. returnValue: {}", sockPair);
        return ResponseEntity.ok(mapper.toChangeQuantityResponse(
            sockPair));
    }

    @Override
    public ResponseEntity<ChangeQuantityResponse> registerSockOutcome(ChangeParametersRequest request) {
        log.info("registerSockOutcome() - start. request: {}", request);

        final SockPair sockPair = service.registerSockOutcome(request.color(), request.cottonPercentage(), request.quantity());

        log.info("registerSockOutcome() - return. returnValue: {}", sockPair);
        return ResponseEntity.ok(mapper.toChangeQuantityResponse(sockPair));
    }

    @Override
    public ResponseEntity<ChangeParametersResponse> changeSockPairParameters(
        UUID id, ChangeParametersRequest request) {
        log.info("changeSockPairParameters() - start. request: {}", request);

        final SockPair sockPair = service.changeSockPairParameters(mapper.toSockPair(id, request));

        log.info("changeSockPairParameters() - return. returnValue: {}", sockPair);
        return ResponseEntity.ok(mapper.toChangeParametersResponse(sockPair));
    }

    @Override
    public ResponseEntity<FilteredQuantityResponse> getFilteredQuantity(FilteredQuantityRequest request) {
        log.info("getFilteredQuantity() - start. request: {}", request);

        final int totalSocksAmount = service.getTotalSocksAmount(request.color(),
            request.operator(), request.cotton());

        log.info("getFilteredQuantity() - return. filteredAmount: {}", totalSocksAmount);
        return ResponseEntity.ok(FilteredQuantityResponse.builder()
            .amount(totalSocksAmount)
            .build());
    }

    @Override
    public ProcessedFileResponse registerFileIncome(MultipartFile file) throws IOException {
        log.info("registerFileIncome() - start. fileName: {}", file.getOriginalFilename());

        final ProcessedFileInfo processedFileInfo = service.processFileIncome(file);

        log.info("registerFileIncome() - return. processed: {}, skipped: {}",
            processedFileInfo.processedRows(), processedFileInfo.rowsSkipped());
        return ProcessedFileResponse.builder()
            .processed(processedFileInfo.processedRows())
            .skipped(processedFileInfo.rowsSkipped())
            .build();
    }
}
