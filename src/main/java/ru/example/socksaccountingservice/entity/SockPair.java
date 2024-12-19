package ru.example.socksaccountingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Сущность, представляющая пару носков на складе.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "socks")
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
    private int quantity;

    /**
     * Процент содержания хлопка.
     */
    @Column(name = "cotton_percentage")
    private int cottonPercentage;

    /**
     * Цвет пары.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    private SockColor sockColor;
}
