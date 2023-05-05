package it.snorcini.dev.orderplanner.validation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class DescriptionDeserializerTest {

    private DescriptionDeserializer descriptionDeserializer;
    private JsonParser p;
    private DeserializationContext ctxt;

    @BeforeEach
    void initMock() {
        descriptionDeserializer = new DescriptionDeserializer();
        p = mock(JsonParser.class);

        ctxt = mock(DeserializationContext.class);
    }

    @Test
    void testDeserialize01() throws IOException {
        doReturn("'test'").when(p).getValueAsString("description");

        String deserialize = descriptionDeserializer.deserialize(p, ctxt);
        assertEquals("&#39;test&#39;", deserialize, "These objects should be equal");
    }

    @Test
    void testDeserialize02() throws IOException {
        doThrow(new IOException()).when(p).getValueAsString("description");


        assertThrows(IOException.class,
                () -> descriptionDeserializer.deserialize(p, ctxt), "These method should be throw an exception");

    }
}
