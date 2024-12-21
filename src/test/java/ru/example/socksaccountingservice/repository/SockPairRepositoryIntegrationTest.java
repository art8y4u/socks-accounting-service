package ru.example.socksaccountingservice.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.example.socksaccountingservice.entity.SockPair;
import ru.example.socksaccountingservice.util.DataUtils;

import java.util.List;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DisplayName("Интеграционный тест репозитория")
class SockPairRepositoryIntegrationTest {

    @Autowired
    private SockPairRepository sockPairRepository;

    @BeforeEach
    void setUp() {
        sockPairRepository.save(DataUtils.FIRST_SOCK_PAIR);
        sockPairRepository.save(DataUtils.SECOND_SOCK_PAIR);
        sockPairRepository.save(DataUtils.THIRD_SOCK_PAIR);
    }

    @AfterEach
    void tearDown() {
        sockPairRepository.deleteAll();
    }

    @Test
    @DisplayName("Тестирование сохранения в репозиторий.")
    void testSaveIntoRepository_whenSavingValidSockPair_thenSavedSuccessfully() {

        final List<SockPair> actual = sockPairRepository.findAll();
        final List<SockPair> expected = List.of(DataUtils.FIRST_SOCK_PAIR, DataUtils.SECOND_SOCK_PAIR, DataUtils.THIRD_SOCK_PAIR);

        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(expected, actual);
    }
}