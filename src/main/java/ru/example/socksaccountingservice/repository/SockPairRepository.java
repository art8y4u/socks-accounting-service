package ru.example.socksaccountingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.example.socksaccountingservice.entity.SockPair;
import ru.example.socksaccountingservice.entity.enums.Color;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс репозитория для работы с сущностью {@link SockPair}.
 * Реализует базовые операции взаимодействия с базой данных.
 */
@Repository
public interface SockPairRepository extends JpaRepository<SockPair, UUID> {

    @Query("""
        SELECT sp
        FROM SockPair sp
        WHERE sp.color = :color
        AND sp.cottonPercentage = :cottonPercentage
        """)
    Optional<SockPair> findSockPairsByColorAndCottonPercentage(
        Color color, int cottonPercentage);

    List<SockPair> findSockPairsByColorAndCottonPercentageIsLessThan(
        Color color, int cottonPercentage);

    List<SockPair> findSockPairsByColorAndCottonPercentageIsGreaterThan(
        Color color, int cottonPercentage);
}
