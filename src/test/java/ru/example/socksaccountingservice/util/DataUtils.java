package ru.example.socksaccountingservice.util;

import ru.example.socksaccountingservice.dto.request.ChangeParametersRequest;
import ru.example.socksaccountingservice.dto.response.ChangeParametersResponse;
import ru.example.socksaccountingservice.dto.response.ChangeQuantityResponse;
import ru.example.socksaccountingservice.entity.SockPair;
import ru.example.socksaccountingservice.entity.enums.Color;
import ru.example.socksaccountingservice.util.constants.Constants;

public final class DataUtils {

    public static final SockPair FIRST_SOCK_PAIR;
    public static final SockPair SECOND_SOCK_PAIR;
    public static final SockPair THIRD_SOCK_PAIR;
    public static final SockPair TO_MODIFY;

    public static final ChangeParametersRequest CHANGE_PARAMETERS_REQUEST;

    public static final ChangeQuantityResponse CHANGE_QUANTITY_RESPONSE;

    public static final ChangeParametersResponse CHANGE_PARAMETERS_RESPONSE;

    static {

        FIRST_SOCK_PAIR = SockPair.builder()
            .id(Constants.FIRST_SOCK_PAIR_ID)
            .cottonPercentage(Constants.COTTON_PERCENTAGE_70)
            .quantity(Constants.QUANTITY_FIRST)
            .color(Color.WHITE)
            .build();

        SECOND_SOCK_PAIR = SockPair.builder()
            .id(Constants.SECOND_SOCK_PAIR_ID)
            .cottonPercentage(Constants.COTTON_PERCENTAGE_80)
            .quantity(Constants.QUANTITY_SECOND)
            .color(Color.BLACK)
            .build();

        THIRD_SOCK_PAIR = SockPair.builder()
            .id(Constants.THIRD_SOCK_PAIR_ID)
            .cottonPercentage(Constants.COTTON_PERCENTAGE_90)
            .quantity(Constants.QUANTITY_THIRD)
            .color(Color.GREY)
            .build();

        TO_MODIFY = SockPair.builder()
            .id(Constants.SECOND_SOCK_PAIR_ID)
            .cottonPercentage(Constants.COTTON_PERCENTAGE_80)
            .quantity(Constants.QUANTITY_SECOND)
            .color(Color.BLACK)
            .build();

        CHANGE_PARAMETERS_REQUEST = ChangeParametersRequest.builder()
            .color(DataUtils.FIRST_SOCK_PAIR.getColor())
            .cottonPercentage(DataUtils.FIRST_SOCK_PAIR.getCottonPercentage())
            .quantity(DataUtils.FIRST_SOCK_PAIR.getQuantity())
            .build();

        CHANGE_QUANTITY_RESPONSE = ChangeQuantityResponse.builder()
            .id(DataUtils.FIRST_SOCK_PAIR.getId())
            .newQuantity(DataUtils.FIRST_SOCK_PAIR.getQuantity())
            .build();

        CHANGE_PARAMETERS_RESPONSE = ChangeParametersResponse.builder()
            .id(DataUtils.FIRST_SOCK_PAIR.getId())
            .color(DataUtils.FIRST_SOCK_PAIR.getColor())
            .quantity(DataUtils.FIRST_SOCK_PAIR.getQuantity())
            .cottonPercentage(DataUtils.FIRST_SOCK_PAIR.getCottonPercentage())
            .build();
    }

    private DataUtils() {
    }
}
