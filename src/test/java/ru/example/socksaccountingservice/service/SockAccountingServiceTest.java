package ru.example.socksaccountingservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.socksaccountingservice.entity.SockPair;
import ru.example.socksaccountingservice.entity.enums.Color;
import ru.example.socksaccountingservice.entity.enums.ComparisonOperator;
import ru.example.socksaccountingservice.repository.SockPairRepository;
import ru.example.socksaccountingservice.util.DataUtils;
import ru.example.socksaccountingservice.util.constants.Constants;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SockAccountingServiceTest {

    @Mock
    private SockPairRepository repository;

    @InjectMocks
    private SockAccountingService service;

    @Test
    @DisplayName("Тестирование увеличения кол-ва существующих носков.")
    void registerSockIncome_shouldUpdateQuantity_whenSockPairExists() {

        when(repository.findSockPairsByColorAndCottonPercentage(Color.BLACK, Constants.COTTON_PERCENTAGE_80))
            .thenReturn(Optional.of(DataUtils.SECOND_SOCK_PAIR));

        SockPair result = service.registerSockIncome(Color.BLACK,
            Constants.COTTON_PERCENTAGE_80, Constants.QUANTITY_SECOND);

        assertEquals(Constants.QUANTITY_SECOND + Constants.QUANTITY_SECOND, result.getQuantity());
        assertEquals(Color.BLACK, result.getColor());
        assertEquals(Constants.SECOND_SOCK_PAIR_ID, result.getId());

        verify(repository, times(1))
            .findSockPairsByColorAndCottonPercentage(Color.BLACK, Constants.COTTON_PERCENTAGE_80);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Тестирование добавления новой сущности и аттрибутов, когда носки не найдены.")
    void registerSockIncome_shouldCreateNewSockPair_whenSockPairDoesNotExist() {

        when(repository.findSockPairsByColorAndCottonPercentage(Color.BLACK, Constants.COTTON_PERCENTAGE_80))
            .thenReturn(Optional.empty());

        SockPair result = service.registerSockIncome(
            Color.BLACK, Constants.COTTON_PERCENTAGE_80, Constants.QUANTITY_SECOND);

        assertEquals(Constants.QUANTITY_SECOND, result.getQuantity());
        assertEquals(Color.BLACK, result.getColor());
        assertEquals(Constants.COTTON_PERCENTAGE_80, result.getCottonPercentage());

        verify(repository, times(1))
            .findSockPairsByColorAndCottonPercentage(any(), anyInt());
        verify(repository, times(1)).save(any(SockPair.class));
    }

    @Test
    @DisplayName("Тестирование уменьшение кол-ва носков, когда они существуют.")
    void registerSockOutcome_shouldReduceQuantity_whenEnoughQuantityExists() {

        when(repository.findSockPairsByColorAndCottonPercentage(Color.BLACK, Constants.COTTON_PERCENTAGE_80))
            .thenReturn(Optional.of(DataUtils.TO_MODIFY));
        when(repository.save(any(SockPair.class))).thenReturn(DataUtils.TO_MODIFY);

        SockPair result = service.registerSockOutcome(
            Color.BLACK, Constants.COTTON_PERCENTAGE_80, Constants.QUANTITY_FIRST);

        assertEquals(Constants.QUANTITY_SECOND - Constants.QUANTITY_FIRST, result.getQuantity());
        assertEquals(Color.BLACK, result.getColor());
        assertEquals(Constants.SECOND_SOCK_PAIR_ID, result.getId());

        verify(repository, times(1))
            .findSockPairsByColorAndCottonPercentage(Color.BLACK, Constants.COTTON_PERCENTAGE_80);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Тестирование выбрасывания исключения, когда кол-ва недостаточно для уменьшения.")
    void registerSockOutcome_shouldThrowException_whenQuantityIsInsufficient() {

        when(repository.findSockPairsByColorAndCottonPercentage(Color.BLACK, Constants.COTTON_PERCENTAGE_80))
            .thenReturn(Optional.of(DataUtils.SECOND_SOCK_PAIR));

        assertThrows(RuntimeException.class, () ->
            service.registerSockOutcome(Color.BLACK, Constants.COTTON_PERCENTAGE_80, Constants.QUANTITY_THIRD));

        verify(repository, times(1))
            .findSockPairsByColorAndCottonPercentage(Color.BLACK, Constants.COTTON_PERCENTAGE_80);
        verify(repository, never()).save(any(SockPair.class));
    }

    @Test
    @DisplayName("Тестирование выбрасывания исключения при уменьшении кол-ва, когда носки не существуют.")
    void registerSockOutcome_shouldThrowException_whenSockPairDoesNotExist() {

        when(repository.findSockPairsByColorAndCottonPercentage(Color.BLACK, Constants.COTTON_PERCENTAGE_80))
            .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.registerSockOutcome(
            Color.BLACK, Constants.COTTON_PERCENTAGE_80, Constants.QUANTITY_THIRD));

        verify(repository, times(1))
            .findSockPairsByColorAndCottonPercentage(Color.BLACK, Constants.COTTON_PERCENTAGE_80);
        verify(repository, never()).save(any(SockPair.class));
    }

    @Test
    @DisplayName("Тестирование сохранения, если сущность с айди существует.")
    void changeSockPairParameters_shouldUpdateParameters_whenIdExists() {

        when(repository.findById(Constants.SECOND_SOCK_PAIR_ID)).thenReturn(Optional.of(DataUtils.TO_MODIFY));
        when(repository.save(DataUtils.SECOND_SOCK_PAIR)).thenReturn(DataUtils.SECOND_SOCK_PAIR);

        SockPair result = service.changeSockPairParameters(DataUtils.SECOND_SOCK_PAIR);

        assertEquals(DataUtils.SECOND_SOCK_PAIR, result);
        verify(repository, times(1)).findById(Constants.SECOND_SOCK_PAIR_ID);
        verify(repository, times(1)).save(DataUtils.SECOND_SOCK_PAIR);
    }

    @Test
    @DisplayName("Тестирование сохранения, если сущность с айди не существует.")
    void changeSockPairParameters_shouldThrowException_whenIdDoesNotExist() {

        when(repository.findById(Constants.SECOND_SOCK_PAIR_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
            () -> service.changeSockPairParameters(DataUtils.SECOND_SOCK_PAIR));

        verify(repository, times(1)).findById(Constants.SECOND_SOCK_PAIR_ID);
        verify(repository, times(0)).save(any(SockPair.class));
    }

    @Test
    @DisplayName("Тестирование вызова правильных методов репозиториев для операторов")
    void getTotalSocksAmount_shouldCalculateTotal_whenOperatorIsLessThan() {

        when(repository.findSockPairsByColorAndCottonPercentageIsLessThan(any(), anyInt()))
            .thenReturn(List.of(DataUtils.FIRST_SOCK_PAIR, DataUtils.SECOND_SOCK_PAIR));
        when(repository.findSockPairsByColorAndCottonPercentageIsGreaterThan(any(), anyInt()))
            .thenReturn(List.of(DataUtils.FIRST_SOCK_PAIR, DataUtils.SECOND_SOCK_PAIR));
        when(repository.findSockPairsByColorAndCottonPercentage(any(), anyInt()))
            .thenReturn(Optional.of(DataUtils.FIRST_SOCK_PAIR));

        service.getTotalSocksAmount(
            Color.BLACK, ComparisonOperator.LESS_THAN, Constants.COTTON_PERCENTAGE_90);

        verify(repository, times(1))
            .findSockPairsByColorAndCottonPercentageIsLessThan(any(), anyInt());

        service.getTotalSocksAmount(
            Color.BLACK, ComparisonOperator.EQUAL, Constants.COTTON_PERCENTAGE_90);
        verify(repository, times(1))
            .findSockPairsByColorAndCottonPercentage(any(), anyInt());

        service.getTotalSocksAmount(
            Color.BLACK, ComparisonOperator.MORE_THAN, Constants.COTTON_PERCENTAGE_90);

        verify(repository, times(1))
            .findSockPairsByColorAndCottonPercentageIsGreaterThan(any(), anyInt());
    }

    @Test
    @DisplayName("Тестирование калькулирования правильной суммы для операции")
    void getTotalSocksAmount_shouldCalculateTotal_whenOperatorIsEqual() {

        when(repository.findSockPairsByColorAndCottonPercentageIsLessThan(any(), anyInt()))
            .thenReturn(List.of(DataUtils.FIRST_SOCK_PAIR, DataUtils.SECOND_SOCK_PAIR));

        int result = service.getTotalSocksAmount(Color.BLACK,
            ComparisonOperator.LESS_THAN, Constants.COTTON_PERCENTAGE_90);

        assertEquals(Constants.QUANTITY_FIRST + Constants.QUANTITY_SECOND, result);
    }
}
