package ru.example.socksaccountingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.example.socksaccountingservice.entity.enums.Color;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Сущность, представляющая справочную таблицу цвета носков.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "colors")
public class SockColor {

    /**
     * Уникальный идентификатор цвета.
     */
    @Id
    @Column(name = "id")
    private UUID id;

    /**
     * Название цвета.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private Color color;
}
