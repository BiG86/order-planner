package it.snorcini.dev.orderplanner.validation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class DescriptionSerializerTest {

    private DescriptionSerializer descriptionDeserializer;
    private JsonGenerator jsonGenerator;
    private SerializerProvider serializerProvider;

    @BeforeEach
    void initMock() {
        descriptionDeserializer = new DescriptionSerializer();
        jsonGenerator = mock(JsonGenerator.class);
        serializerProvider = mock(SerializerProvider.class);


    }

    @Test
    void testSerialize01() throws IOException {
        doNothing().when(jsonGenerator).writeString(anyString());
        descriptionDeserializer.serialize("test", jsonGenerator, serializerProvider);
        verify(jsonGenerator, times(1)).writeString("test");
    }


}
