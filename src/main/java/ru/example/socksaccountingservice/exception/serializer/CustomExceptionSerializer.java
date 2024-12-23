package ru.example.socksaccountingservice.exception.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonObjectSerializer;
import ru.example.socksaccountingservice.exception.impl.CustomException;
import ru.example.socksaccountingservice.exception.util.SerializationConstants;

import java.io.IOException;

/**
 * Класс, сериализующий исключения в JSON-формат.
 */
public class CustomExceptionSerializer extends JsonObjectSerializer<CustomException> {

    /**
     * Сериализует объект исключения {@link CustomException}.
     * <p>
     * Записывает в JSON поля типа и описания исключения.
     */
    @Override
    protected void serializeObject(CustomException exception, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeFieldName(SerializationConstants.TYPE_KEY);
        jgen.writeString(exception.type());

        jgen.writeFieldName(SerializationConstants.DESCRIPTION_KEY);
        jgen.writeString(exception.description());
    }
}
