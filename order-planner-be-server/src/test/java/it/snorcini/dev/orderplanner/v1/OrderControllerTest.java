package it.snorcini.dev.orderplanner.v1;

import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.OrderListResponse;
import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OrderController.class})
@DisplayName("Tests regarding the OrderController class")
class OrderControllerTest {

    @Autowired
    private OrderController target;
    @MockBean
    private OrderService orderServiceMock;
    @Mock
    private OrderDTO orderDTOMock;
    @Mock
    private OrderPlannerBaseResponse responseMock;
    @Mock
    private OrderListResponse responseListMock;
    @Mock
    private UpdateOrderDTO updateOrderDTOMock;


    @Test
    @DisplayName("Should call getFilteredList and return OK")
    void testGetFilteredList01() {
        // given
        doReturn(responseListMock).when(orderServiceMock).getOrders(any());

        // when
        ResponseEntity<OrderListResponse> response = target
                .getFilteredList(null);

        // then
        verify(orderServiceMock, times(1)).getOrders(any());
        assertNotNull(response.getBody(), "These objects should be not null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "These objects should be equal");
    }

    @Test
    @DisplayName("Should call saveOrder and return OK")
    void testSaveOrder01() {
        // given
        doReturn(responseMock).when(orderServiceMock).saveOrder(orderDTOMock);

        // when
        ResponseEntity<OrderPlannerBaseResponse> response = target
                .saveOrder(orderDTOMock);

        // then
        verify(orderServiceMock, times(1)).saveOrder(orderDTOMock);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "These objects should be equal");
    }

    @Test
    @DisplayName("Should call updateOrder and return OK")
    void testUpdateOrder01() {
        // given
        doReturn(responseMock).when(orderServiceMock).updateOrder(updateOrderDTOMock);

        // when
        ResponseEntity<OrderPlannerBaseResponse> response = target.updateOrder(updateOrderDTOMock);

        // then
        verify(orderServiceMock, times(1)).updateOrder(updateOrderDTOMock);
        assertNotNull(response.getBody(), "These objects should be not null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "These objects should be equal");
    }
}
