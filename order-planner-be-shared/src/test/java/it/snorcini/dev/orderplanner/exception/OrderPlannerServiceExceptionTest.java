package it.snorcini.dev.orderplanner.exception;

import it.snorcini.dev.orderplanner.result.OrderPlannerResults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Tests regarding the orderplannerServiceException class")
class OrderPlannerServiceExceptionTest {

    private OrderPlannerServiceException orderPlannerServiceException;
    private final OrderPlannerResults orderPlannerResultsMock = OrderPlannerResults.GENERIC_ERROR;
    private final List<String> resultInfoMock = List.of("result", "Info", "Mock");
    private final Class<RuntimeException> responseClassMock = RuntimeException.class;

    @Test
    @DisplayName("Should set each field using setters")
    void testorderplannerServiceException01() {
        // given
        orderPlannerServiceException = new OrderPlannerServiceException();

        // when
        orderPlannerServiceException.setOrderPlannerResults(orderPlannerResultsMock);
        orderPlannerServiceException.setResultInfo(resultInfoMock);
        orderPlannerServiceException.setResponseClass(responseClassMock);

        // then
        assertEquals(orderPlannerResultsMock, orderPlannerServiceException.getOrderPlannerResults(), "These objects should be equal");
        assertEquals(resultInfoMock, orderPlannerServiceException.getResultInfo(), "These objects should be equal");
        assertEquals(responseClassMock, orderPlannerServiceException.getResponseClass(), "These objects should be equal");
    }

    @Test
    @DisplayName("Should initialize an empty orderplannerServiceException")
    void testorderplannerServiceException02() {
        // when
        orderPlannerServiceException = new OrderPlannerServiceException();

        // then
        assertNull(orderPlannerServiceException.getOrderPlannerResults() , "These objects should be equal");
        assertNull(orderPlannerServiceException.getResultInfo(), "These objects should be null");
        assertNull(orderPlannerServiceException.getResponseClass(), "These objects should be equal");
    }

}
