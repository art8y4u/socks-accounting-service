package ru.example.socksaccountingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.socksaccountingservice.entity.SockColor;

import java.util.UUID;

/**
 * Интерфейс репозитория для работы с сущностью {@link SockColor}.
 * Реализует базовые операции взаимодействия с базой данных.
 */
public interface SockColorRepository extends JpaRepository<SockColor, UUID> {
}
