package it.snorcini.dev.orderplanner.service;

import com.querydsl.core.types.Predicate;
import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.OrderListResponse;
import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.dto.PlanResponse;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.entity.Coordinate;
import it.snorcini.dev.orderplanner.entity.Depot;
import it.snorcini.dev.orderplanner.entity.Order;
import it.snorcini.dev.orderplanner.entity.OrderStatus;
import it.snorcini.dev.orderplanner.entity.Package;
import it.snorcini.dev.orderplanner.exception.OrderPlannerServiceException;
import it.snorcini.dev.orderplanner.mapper.OrderMapper;
import it.snorcini.dev.orderplanner.repository.DepotRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private final String depotId = "2L";
    private OrderServiceImplementation target;
    private OrderServiceImplementation orderServiceImplementationSpy;
    @MockBean
    private OrderRepository orderRepositoryMock;
    @MockBean
    private DepotRepository depotRepositoryMock;
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
    private List<Package> packagesList2;
    @Mock
    private List<Order> orderListMock;

    @BeforeEach
    public void initialize() {
        // Initialize objects
        packagesList = new ArrayList<>();
        packagesList.add(Package.builder().description("testDescription")
                .destination(Coordinate.builder().latitude(2.12).longitude(2.3).build()).build());
        packagesList.add(Package.builder().description("testDescription2")
                .destination(Coordinate.builder().latitude(4.12).longitude(1.37).build()).build());
        packagesList2 = new ArrayList<>();
        packagesList2.add(Package.builder().description("testDescription4")
                .destination(Coordinate.builder().latitude(5.17).longitude(8.14).build()).build());
        packagesList2.add(Package.builder().description("testDescription4")
                .destination(Coordinate.builder().latitude(3.72).longitude(9.37).build()).build());
        orderDTO = new OrderDTO();
        updateOrderDTO = new UpdateOrderDTO();
        orderDTO.setPackages(packagesList);
        updateOrderDTO.setUid(orderId);
        updateOrderDTO.setPackages(packagesList);
        updateOrderDTO.setStatus(OrderStatus.INITIAL);
        orderList = new ArrayList<>();
        orderList.add(Order.builder().packages(packagesList).build());
        orderList.add(Order.builder().packages(packagesList2).build());
        doReturn(orderList.stream()).when(orderListMock).stream();

        // Instantiate test target and spy
        target = new OrderServiceImplementation(
                orderRepositoryMock,
                depotRepositoryMock,
                orderMapperMock,
                null,
                null
        );
        orderServiceImplementationSpy = spy(target);
    }

    @Test
    @DisplayName("When asking for an Order the test should retrieve an Order inside a list")
    void testGetOrders01() {
        //PREPARE
        doReturn(orderListMock).when(orderRepositoryMock).findAll();

        //RUN & VERIFY
        OrderListResponse orderListResponse = target.getOrders(null);

        assertEquals(orderListResponse.getPayload().size(), orderList.size(), "These objects should be equal");
        verify(orderRepositoryMock, times(1)).findAll();
        verify(orderMapperMock, times(orderList.size())).orderToDetailOrderDTO(any(Order.class));
    }

    @Test
    @DisplayName("When asking for a filtered Order the test should retrieve an Order inside a list")
    void testGetOrders02() {
        //PREPARE
        doReturn(orderListMock).when(orderRepositoryMock).findAll(any(Predicate.class));

        //RUN & VERIFY
        OrderListResponse orderListResponse = target.getOrders(OrderStatus.INITIAL);

        assertEquals(orderListResponse.getPayload().size(), orderList.size(), "These objects should be equal");
        verify(orderRepositoryMock, times(1)).findAll(any(Predicate.class));
        verify(orderMapperMock, times(orderList.size())).orderToDetailOrderDTO(any(Order.class));
    }

    @Test
    @DisplayName("Should call save and setOperationResult with success")
    void testSaveOrder02() {
        //PREPARE
        doReturn(Optional.empty()).when(orderRepositoryMock)
                .findByUid(anyString());
        doReturn(orderMock).when(orderMapperMock)
                .orderDtoToOrder(any());
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
    @DisplayName("Should throw OrderPlannerServiceException with element not found when findByUid " +
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
    @DisplayName("Should call updateOrder, save and setOperationResult with success")
    void testUpdateOrder03() {
        //PREPARE
        doReturn(Optional.of(orderMock)).when(orderRepositoryMock).findByUid(orderId);
        doReturn(orderId).when(orderMock).getUid();
        doReturn(orderMock).when(orderMapperMock).updateOrderDTOToOrderEntity(
                any(),
                any()
        );

        //RUN
        OrderPlannerBaseResponse result = orderServiceImplementationSpy.updateOrder(updateOrderDTO);

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

    @Test
    @DisplayName("The test should retrieve the delivery plan")
    void testStartPlan01() {
        //PREPARE
        Depot depot = Depot.builder().uid(depotId).latitude(1.0).longitude(1.0).build();
        doReturn(Optional.of(depot)).when(depotRepositoryMock).findByUid(anyString());
        doReturn(orderList).when(orderRepositoryMock).findAll();

        //RUN
        PlanResponse response = target.startPlan(depotId);

        //VERIFY
        assertNotNull(response);
        verify(orderRepositoryMock, times(1)).findAll();
        verify(depotRepositoryMock, times(1)).findByUid(anyString());
    }
}
