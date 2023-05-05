package it.snorcini.dev.orderplanner.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDTOTest {

    private OrderDTO orderDTO;

    @Test
    void testBookDTO01() {
        orderDTO = new OrderDTO();
        String toStringed = orderDTO.toString();
        assertThat(toStringed).overridingErrorMessage("This element should not be null").isNotNull();
    }

    @Test
    void testBookDTO02() {
        orderDTO = new OrderDTO("isbn test", "title test", "author test");
        OrderDTO orderDTOSetter = new OrderDTO();
        orderDTOSetter.setIsbn("isbn test");
        orderDTOSetter.setTitle("title test");
        orderDTOSetter.setAuthor("author test");
        assertEquals(orderDTOSetter, orderDTO, "These objects should be equal");
    }

}
