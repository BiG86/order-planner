package it.snorcini.dev.orderplanner.validation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.regex.Pattern;

/**
 * Custom JSON serializer to encrypt the description field.
 * Replace all &#39; with '
 */
@NoArgsConstructor
public class DescriptionSerializer extends JsonSerializer<String> {

    public static final String REPLACEMENT = "'";
    private static final Pattern PATTERN = Pattern.compile("&#39;");


    /**
     * Serialization method overridden to sanitize the encrypted field.
     *
     * @param s                  Description to serialize
     * @param jsonGenerator      Json generator
     * @param serializerProvider Serliazition provider
     * @return the encrypted description
     */
    @SneakyThrows
    @Override
    public void serialize(final String s, final JsonGenerator jsonGenerator
            , final SerializerProvider serializerProvider) {
        jsonGenerator.writeString(PATTERN.matcher(s).replaceAll(REPLACEMENT));
    }
}
