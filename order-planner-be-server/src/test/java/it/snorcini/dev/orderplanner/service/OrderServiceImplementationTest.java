package it.snorcini.dev.orderplanner.service;

import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.OrderListResponse;
import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.entity.Order;
import it.snorcini.dev.orderplanner.entity.Package;
import it.snorcini.dev.orderplanner.exception.OrderPlannerServiceException;
import it.snorcini.dev.orderplanner.repository.OrderRepository;
import it.snorcini.dev.orderplanner.result.OrderPlannerResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests regarding the OrderServiceImplementation class")
class OrderServiceImplementationTest {

    private final String orderId = "1L";
    private OrderServiceImplementation target;
    private OrderServiceImplementation orderServiceImplementationSpy;
    @MockBean
    private OrderRepository orderRepositoryMock;
    @MockBean
    private OrderMapper orderMapperMock;
    @Mock
    private Order orderMock;
    @Mock
    private OrderDTO orderDTOMock;
    private OrderDTO orderDTO;
    private UpdateOrderDTO updateOrderDTO;
    private List<Order> orderList;
    private List<Package> packagesList;
    @Mock
    private List<Order> orderListMock;

    @BeforeEach
    public void initialize() {
        // Initialize objects
        packagesList = new ArrayList<>();
        packagesList.add(Package.builder().build());
        orderDTO = new OrderDTO();
        updateOrderDTO = new UpdateOrderDTO();
        orderDTO.setPackages(packagesList);
        updateOrderDTO.setUid(orderId);
        updateOrderDTO.setPackages(packagesList);
        orderList = new ArrayList<>();
        orderList.add(Order.builder().build());
        doReturn(orderList.stream()).when(orderListMock).stream();

        // Instantiate test target and spy
        target = new OrderServiceImplementation(
                orderRepositoryMock,
                orderMapperMock
        );
        orderServiceImplementationSpy = spy(target);
    }

    @Test
    @DisplayName("When asking for an Order the test should retrieve an Order inside a list")
    void testGetOrders01() {
        //PREPARE
        doReturn(orderListMock).when(orderRepositoryMock).findAll();

        //RUN & VERIFY
        OrderListResponse orderListResponse = target.getOrders();

        assertEquals(orderListResponse.getPayload().size(), orderList.size(), "These objects should be equal");
        verify(orderRepositoryMock, times(1)).findAll();
        verify(orderMapperMock, times(1)).orderToDetailOrderDTO(any(Order.class));
    }

    @Test
    @DisplayName("Should call save and setOperationResult with success")
    void testSaveOrder02() {
        //PREPARE
        doReturn(Optional.empty()).when(orderRepositoryMock)
                .findByUid(anyString());
        doReturn(orderMock).when(orderMapperMock)
                .orderDtoToOrder(
                        orderDTOMock);
        doReturn(new OrderPlannerBaseResponse()).when(orderServiceImplementationSpy)
                .setOperationResult(any(), any(), any());

        //RUN
        orderServiceImplementationSpy.saveOrder(orderDTO);

        //VERIFY
        verify(orderRepositoryMock, times(1)).save(any());
        verify(orderServiceImplementationSpy, times(1))
                .setOperationResult(any(), any(), any());
    }

    @Test
    @DisplayName("Should throw OrderPlannerServiceException with element not found when findByIdAndDateDeleteIsNull " +
            "returns no elements")
    void testUpdateOrder01() {
        //PREPARE
        doReturn(Optional.empty()).when(orderRepositoryMock).findById(orderId);

        //RUN
        OrderPlannerServiceException orderPlannerServiceException = assertThrows(
                OrderPlannerServiceException.class,
                () -> target.updateOrder(updateOrderDTO), "Should throw an OrderPlannerServiceException");

        //VERIFY
        assertEquals(OrderPlannerResults.ORDER_NOT_FOUND, orderPlannerServiceException.getOrderPlannerResults(), "These objects should be equal");
        assertNull(orderPlannerServiceException.getResultInfo(), "These objects should be null");
        assertEquals(OrderPlannerBaseResponse.class, orderPlannerServiceException.getResponseClass(), "These objects should be equal");
    }

    @Test
    @DisplayName("Should call findFirstByCodeAndDateDeleteIsNull, save and setOperationResult with success")
    void testUpdateOrder03() {
        //PREPARE
        doReturn(Optional.of(orderMock)).when(orderRepositoryMock).findByUid(orderId);
        doReturn(orderId).when(orderMock).getUid();
        doReturn(orderMock).when(orderMapperMock).updateOrderDTOToOrderEntity(
                any(),
                any()
        );

        //RUN
        OrderPlannerBaseResponse result = orderServiceImplementationSpy.updateOrder(
                updateOrderDTO);

        //VERIFY
        assertEquals(0, result.getResultCode(), "These objects should be equal");
        verify(orderRepositoryMock, times(1)).findByUid(anyString());
        verify(orderRepositoryMock, times(1)).save(orderMock);
        verify(orderServiceImplementationSpy, times(1)).setOperationResult(
                any(),
                any(),
                any()
        );
    }

    @Test
    @DisplayName("Should call findFirstByCodeAndDateDeleteIsNull, save and setOperationResult with success")
    void testUpdateOrder04() {
        //PREPARE
        doReturn(Optional.of(orderMock)).when(orderRepositoryMock).findById(orderId);
        doReturn(orderId).when(orderMock).getUid();
        Order fromDb = Order.builder().build();
        fromDb.setUid(orderId);
        doReturn(Optional.of(fromDb)).when(orderRepositoryMock).findByUid(anyString());
        doReturn(orderMock).when(orderMapperMock).updateOrderDTOToOrderEntity(
                any(),
                any()
        );

        //RUN
        OrderPlannerBaseResponse result = orderServiceImplementationSpy.updateOrder(
                updateOrderDTO);

        //VERIFY
        assertEquals(0, result.getResultCode(), "These objects should be equal");
        verify(orderRepositoryMock, times(1)).findByUid(anyString());
        verify(orderRepositoryMock, times(1)).save(orderMock);
        verify(orderServiceImplementationSpy, times(1)).setOperationResult(
                any(),
                any(),
                any()
        );
    }
}
