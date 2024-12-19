package ru.example.socksaccountingservice.util;

import ru.example.socksaccountingservice.entity.SockColor;
import ru.example.socksaccountingservice.entity.SockPair;
import ru.example.socksaccountingservice.entity.enums.Color;
import ru.example.socksaccountingservice.util.constants.Constants;

public final class DataUtils {

    public static final SockColor BLACK_COLOR;
    public static final SockColor WHITE_COLOR;
    public static final SockColor GREY_COLOR;

    public static final SockPair FIRST_SOCK_PAIR;
    public static final SockPair SECOND_SOCK_PAIR;
    public static final SockPair THIRD_SOCK_PAIR;

    static {

        BLACK_COLOR = SockColor.builder()
            .id(Constants.BLACK_COLOR_ID)
            .color(Color.BLACK)
            .build();

        WHITE_COLOR = SockColor.builder()
            .id(Constants.WHITE_COLOR_ID)
            .color(Color.WHITE)
            .build();

        GREY_COLOR = SockColor.builder()
            .id(Constants.GREY_COLOR_ID)
            .color(Color.GREY)
            .build();

        FIRST_SOCK_PAIR = SockPair.builder()
            .id(Constants.FIRST_SOCK_PAIR_ID)
            .cottonPercentage(Constants.COTTON_PERCENTAGE_70)
            .quantity(Constants.QUANTITY_FIRST)
            .sockColor(WHITE_COLOR)
            .build();

        SECOND_SOCK_PAIR = SockPair.builder()
            .id(Constants.SECOND_SOCK_PAIR_ID)
            .cottonPercentage(Constants.COTTON_PERCENTAGE_80)
            .quantity(Constants.QUANTITY_SECOND)
            .sockColor(BLACK_COLOR)
            .build();

        THIRD_SOCK_PAIR = SockPair.builder()
            .id(Constants.THIRD_SOCK_PAIR_ID)
            .cottonPercentage(Constants.COTTON_PERCENTAGE_90)
            .quantity(Constants.QUANTITY_THIRD)
            .sockColor(GREY_COLOR)
            .build();
    }

    private DataUtils() {
    }
}
