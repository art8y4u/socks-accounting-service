package ru.example.socksaccountingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.example.socksaccountingservice.entity.enums.Color;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

/**
 * Сущность, представляющая пару носков на складе.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "socks",
    schema = "socks_service",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"color", "cotton_percentage"})})
public class SockPair {

    /**
     * Уникальный идентификатор пары носков.
     */
    @Id
    @Column(name = "id")
    private UUID id;

    /**
     * Количество пар на складе.
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * Процент содержания хлопка.
     */
    @Column(name = "cotton_percentage")
    private int cottonPercentage;

    /**
     * Цвет пары.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;
}
