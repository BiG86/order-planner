package it.snorcini.dev.orderplanner.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateOrderDTOTest {

    private UpdateOrderDTO updateBookDTO;

    @Test
    void testUpdateBookDTOTest01() {
        updateBookDTO = new UpdateOrderDTO();
        String toStringed = updateBookDTO.toString();
        assertThat(toStringed).overridingErrorMessage("This element should not be null").isNotNull();
    }

    @Test
    void testUpdateBookDTOTest02() {
        long id = 1L;
        updateBookDTO = new UpdateOrderDTO(id);
        UpdateOrderDTO updateBookDTOSetter = new UpdateOrderDTO();
        updateBookDTOSetter.setId(id);
        assertEquals(updateBookDTOSetter, updateBookDTO, "These objects should be equal");
    }
}
