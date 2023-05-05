package it.snorcini.dev.orderplanner.validation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.regex.Pattern;

/**
 * Custom JSON deserializer to decrypt the description field.
 * Replace all ' with &#39;
 */
@NoArgsConstructor
public class DescriptionDeserializer extends JsonDeserializer<String> {

    public static final String REPLACEMENT = "&#39;";
    private static final Pattern PATTERN = Pattern.compile("'");


    /**
     * Deserialization method overridden to sanitize the decryption field.
     *
     * @param p    The json parser
     * @param ctxt The deserialization context
     * @return the decrypted description
     */
    @SneakyThrows
    @Override
    public String deserialize(final JsonParser p,
                              final DeserializationContext ctxt) {
        return PATTERN.matcher(p.getValueAsString("description")).replaceAll(REPLACEMENT);
    }
}
