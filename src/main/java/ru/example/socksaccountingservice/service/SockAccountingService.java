package ru.example.socksaccountingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.example.socksaccountingservice.dto.ProcessedFileInfo;
import ru.example.socksaccountingservice.entity.SockPair;
import ru.example.socksaccountingservice.entity.enums.Color;
import ru.example.socksaccountingservice.entity.enums.ComparisonOperator;
import ru.example.socksaccountingservice.repository.SockPairRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Сервис для управления учетными операциями с носками.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SockAccountingService {

    /**
     * Репозиторий для работы с сущностями {@link SockPair}
     */
    private final SockPairRepository repository;

    /**
     * Регистрирует поступление носков на склад.
     *
     * @param color            цвет носков
     * @param cottonPercentage процентное содержание хлопка в носках
     * @param quantity         количество поступивших носков
     * @return обновленный или созданный объект {@link SockPair}
     */
    @Transactional
    public SockPair registerSockIncome(Color color, int cottonPercentage, int quantity) {
        log.debug("registerSockIncome() - start. params: color={}, cotton={}, quantity={}",
            color, cottonPercentage, quantity);

        final SockPair sockPair = repository.findSockPairsByColorAndCottonPercentage(color, cottonPercentage)
            .orElseGet(() -> SockPair.builder()
                .id(UUID.randomUUID())
                .color(color)
                .cottonPercentage(cottonPercentage)
                .quantity(0)
                .build());

        sockPair.setQuantity(sockPair.getQuantity() + quantity);
        repository.save(sockPair);

        log.debug("registerSockIncome() - return. returnValue: {}", sockPair);
        return sockPair;
    }

    /**
     * Регистрирует расход носков со склада.
     *
     * @param color            цвет носков
     * @param cottonPercentage процентное содержание хлопка
     * @param quantity         количество списываемых носков
     * @return обновленный объект {@link SockPair}
     * @throws RuntimeException если пара носков с заданными параметрами не найдена или на складе
     *                          недостаточное количество для списания
     */
    @Transactional
    public SockPair registerSockOutcome(Color color, int cottonPercentage, int quantity) {
        log.debug("registerSockOutcome() - start. params: params: color: {}, cotton: {}, quantity: {}",
            color, cottonPercentage, quantity);

        final SockPair sockPair = repository.findSockPairsByColorAndCottonPercentage(color, cottonPercentage)
            .orElseThrow(() -> {
                log.error("registerSockOutcome() - error. params: color: {}, cotton: {}, quantity: {}",
                    color, cottonPercentage, quantity);
                return new RuntimeException("Not found exception");
            });

        final int currentQuantity = sockPair.getQuantity();

        if (currentQuantity < quantity) {
            log.error("registerSockOutcome() - error. Not enough quantity. color: {}, cotton: {}, quantity: {}",
                color, cottonPercentage, quantity);
            throw new RuntimeException();
        }

        sockPair.setQuantity(currentQuantity - quantity);
        log.debug("registerSockOutcome() - return. returnValue: {}", sockPair);

        return repository.save(sockPair);
    }

    /**
     * Изменяет параметры существующей пары носков.
     *
     * @param sockPair объект {@link SockPair} с обновленными параметрами
     * @return обновленный объект {@link SockPair}
     * @throws RuntimeException если пара носков с указанным идентификатором не найдена
     */
    @Transactional
    public SockPair changeSockPairParameters(SockPair sockPair) {
        log.debug("changeSockPairParameters() - start. id: {}", sockPair.getId());

        final UUID id = sockPair.getId();

        repository.findById(id)
            .orElseThrow(() -> {
                log.error("changeSockPairParameters() - error. Not found with id: {}", id);
                return new RuntimeException();
            });

        final SockPair updated = repository.save(sockPair);
        log.debug("changeSockPairParameters() - return. returnValue: {}", updated);

        return updated;
    }

    /**
     * Подсчитывает общее количество носков на складе по заданным параметрам.
     *
     * @param color            цвет носков
     * @param type             оператор сравнения для процентного содержания хлопка (меньше, равно, больше)
     * @param cottonPercentage процентное содержание хлопка
     * @return общее количество носков, соответствующих фильтру
     * @throws RuntimeException если не найдены пары носков для фильтрации с использованием оператора равенства
     */
    public int getTotalSocksAmount(Color color, ComparisonOperator type, int cottonPercentage) {
        log.debug("getTotalSocksAmount() - start. params: color: {}, comparisonType: {}, cottonPercentage: {}",
            color, type, cottonPercentage);

        final List<SockPair> list = switch (type) {
            case LESS_THAN -> repository.findSockPairsByColorAndCottonPercentageIsLessThan(color, cottonPercentage);
            case EQUAL -> repository.findSockPairsByColorAndCottonPercentage(color, cottonPercentage)
                .map(List::of)
                .orElseThrow();
            case MORE_THAN -> repository.findSockPairsByColorAndCottonPercentageIsGreaterThan(color, cottonPercentage);
        };

        final int sum = list.stream()
            .mapToInt(SockPair::getQuantity)
            .sum();

        log.debug("getTotalSocksAmount() - return. filteredSum: {}", sum);
        return sum;
    }

    /**
     * Обрабатывает Excel-файл с данными о поступлении носков.
     *
     * @param file Excel-файл с данными о цвете, процентном содержании хлопка и количестве носков
     * @return {@link ProcessedFileInfo} о количестве обработанных строк и пропущенных записей
     * @throws RuntimeException если возникла ошибка при обработке файла
     */
    @Transactional
    public ProcessedFileInfo processFileIncome(MultipartFile file) {
        log.debug("processFileIncome() - start. fileName: {}", file.getOriginalFilename());

        int numSkipped = 0;
        List<SockPair> processedList = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {

            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    numSkipped = processRow(row, processedList, numSkipped);
                }
            }
        } catch (IOException e) {
            log.error("processFileIncome() - error. fileName: {}", file.getOriginalFilename());
            throw new RuntimeException(e);
        }

        repository.saveAll(processedList);
        final int processed = processedList.size();

        log.debug("processFileIncome() - return. processedRows: {}, skippedRows: {}", processed, numSkipped);
        return ProcessedFileInfo.builder()
            .processedRows(processed)
            .rowsSkipped(numSkipped)
            .build();
    }

    /**
     * Вспомогательный метод для ряда таблицы.
     *
     * @param row           строка из Excel-таблицы
     * @param processedList список уже обработанных пар носков
     * @param numSkipped    количество пропущенных строк
     * @return обновленное количество пропущенных строк
     */
    private int processRow(Row row, List<SockPair> processedList, int numSkipped) {
        log.debug("processRow() - start. row: {}", row);

        try {
            if (row.getPhysicalNumberOfCells() != 3) {
                throw new IllegalArgumentException();
            }

            final Color color = Color.valueOf(row.getCell(0).getStringCellValue()
                .trim()
                .toUpperCase());
            final int cottonPercentage = (int) row.getCell(1).getNumericCellValue();
            final int quantity = (int) row.getCell(2).getNumericCellValue();

            processedList.add(SockPair.builder()
                .id(UUID.randomUUID())
                .color(color)
                .cottonPercentage(cottonPercentage)
                .quantity(quantity)
                .build());
        } catch (IllegalArgumentException | NoSuchElementException e) {
            log.warn("processFileIncome() - warn. skippedRow: {}", row.getRowNum());
            numSkipped++;
        }

        log.debug("processRow() - return. skipped: {}", numSkipped);
        return numSkipped;
    }
}
