package it.snorcini.dev.orderplanner.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class OrderPlannerBaseResponseTest {

    private OrderPlannerBaseResponse OrderPlannerBaseResponse;

    @Test
    void testorderplannerBaseResponse01() {
        OrderPlannerBaseResponse = new OrderPlannerBaseResponse();
        String toStringed = OrderPlannerBaseResponse.toString();
        assertThat(toStringed).overridingErrorMessage("This element should not be null").isNotNull();
    }

    @Test
    void testorderplannerBaseResponse02() {
        int resultCode = 1;
        String resultMessage = "resultMessage";
        List<String> resultInfo = Arrays.asList("resultInfo");
        OrderPlannerBaseResponse = new OrderPlannerBaseResponse(resultCode, resultMessage, resultInfo);
        OrderPlannerBaseResponse OrderPlannerBaseResponseSetter = new OrderPlannerBaseResponse();
        OrderPlannerBaseResponseSetter.setResultCode(resultCode);
        OrderPlannerBaseResponseSetter.setResultMessage(resultMessage);
        OrderPlannerBaseResponseSetter.setResultInfo(resultInfo);
        assertEquals(OrderPlannerBaseResponseSetter, OrderPlannerBaseResponse, "These objects should be equal");
    }


}
