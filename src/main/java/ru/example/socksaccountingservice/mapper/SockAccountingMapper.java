package ru.example.socksaccountingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.example.socksaccountingservice.dto.request.ChangeParametersRequest;
import ru.example.socksaccountingservice.dto.response.ChangeParametersResponse;
import ru.example.socksaccountingservice.dto.response.ChangeQuantityResponse;
import ru.example.socksaccountingservice.entity.SockPair;

import java.util.UUID;

/**
 * Маппер для преобразования объектов, связанных с учетом носков.
 */
@Mapper(componentModel = "spring")
public abstract class SockAccountingMapper {

    /**
     * Преобразует объект {@link SockPair} в объект ответа {@link ChangeQuantityResponse}.
     *
     * @param sockPair объект, содержащий информацию о паре носков
     * @return объект {@link ChangeQuantityResponse}, содержащий новое количество и идентификатор
     */
    @Mapping(target = "newQuantity", source = "quantity")
    @Mapping(target = "message", ignore = true)
    public abstract ChangeQuantityResponse toChangeQuantityResponse(SockPair sockPair);

    /**
     * Преобразует идентификатор и параметры изменения в объект {@link SockPair}.
     *
     * @param id      уникальный идентификатор носков
     * @param request объект {@link ChangeParametersRequest}, содержащий параметры для изменения
     * @return объект {@link SockPair}, представляющий обновленные данные пары носков
     */
    public abstract SockPair toSockPair(UUID id, ChangeParametersRequest request);

    /**
     * Преобразует объект {@link SockPair} в объект ответа {@link ChangeParametersResponse}.
     *
     * @param sockPair объект, содержащий информацию о паре носков
     * @return объект {@link ChangeParametersResponse}, содержащий параметры изменений
     */
    @Mapping(target = "message", ignore = true)
    public abstract ChangeParametersResponse toChangeParametersResponse(SockPair sockPair);
}
