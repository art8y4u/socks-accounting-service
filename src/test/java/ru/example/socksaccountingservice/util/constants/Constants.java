package ru.example.socksaccountingservice.util.constants;

import java.util.UUID;

public final class Constants {

    public static final UUID BLACK_COLOR_ID;
    public static final UUID WHITE_COLOR_ID;
    public static final UUID GREY_COLOR_ID;

    public static final UUID FIRST_SOCK_PAIR_ID;
    public static final UUID SECOND_SOCK_PAIR_ID;
    public static final UUID THIRD_SOCK_PAIR_ID;

    public static final int COTTON_PERCENTAGE_70;
    public static final int COTTON_PERCENTAGE_80;
    public static final int COTTON_PERCENTAGE_90;

    public static final int QUANTITY_FIRST;
    public static final int QUANTITY_SECOND;
    public static final int QUANTITY_THIRD;

    static {
        BLACK_COLOR_ID = UUID.randomUUID();
        WHITE_COLOR_ID = UUID.randomUUID();
        GREY_COLOR_ID = UUID.randomUUID();

        FIRST_SOCK_PAIR_ID = UUID.randomUUID();
        SECOND_SOCK_PAIR_ID = UUID.randomUUID();
        THIRD_SOCK_PAIR_ID = UUID.randomUUID();

        COTTON_PERCENTAGE_70 = 70;
        COTTON_PERCENTAGE_80 = 80;
        COTTON_PERCENTAGE_90 = 90;

        QUANTITY_THIRD = 20;
        QUANTITY_SECOND = 15;
        QUANTITY_FIRST = 10;
    }

    private Constants() {
    }
}
