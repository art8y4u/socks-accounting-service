package ru.example.socksaccountingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.socksaccountingservice.entity.SockPair;

import java.util.UUID;

/**
 * Интерфейс репозитория для работы с сущностью {@link SockPair}.
 * Реализует базовые операции взаимодействия с базой данных.
 */
public interface SockPairRepository extends JpaRepository<SockPair, UUID> {
}
