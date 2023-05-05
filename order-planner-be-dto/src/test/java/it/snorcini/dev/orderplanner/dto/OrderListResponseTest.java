package it.snorcini.dev.orderplanner.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class OrderListResponseTest {

    @Test
    void testBookListResponse01() {
        OrderListResponse listResponse = new OrderListResponse();
        listResponse.setPayload(new ArrayList<>());
        String toStringed = listResponse.toString();
        assertThat(toStringed).overridingErrorMessage("This element should not be null").isNotNull();
    }

}
