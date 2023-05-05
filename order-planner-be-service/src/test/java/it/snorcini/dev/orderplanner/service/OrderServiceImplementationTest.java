package it.snorcini.dev.orderplanner.service;

import it.snorcini.dev.orderplanner.OrderMapper;
import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.OrderListResponse;
import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.entity.Order;
import it.snorcini.dev.orderplanner.exception.OrderPlannerServiceException;
import it.snorcini.dev.orderplanner.repository.OrderRepository;
import it.snorcini.dev.orderplanner.result.OrderPlannerResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests regarding the BookServiceImplementation class")
class OrderServiceImplementationTest {

    private final String username = "usernameMock";
    private final OffsetDateTime offsetDateTime = OffsetDateTime.now();
    private final Long bookId = 1L;
    private OrderServiceImplementation target;
    private OrderServiceImplementation orderServiceImplementationSpy;
    @MockBean
    private OrderRepository orderRepositoryMock;
    @MockBean
    private OrderMapper bookMapperMock;
    @Mock
    private Order orderMock;
    @Mock
    private OrderDTO orderDTOMock;
    private OrderDTO orderDTO;
    private UpdateOrderDTO updateBookDTO;
    private List<Order> orderList;
    @Mock
    private Page<Order> bookPage;

    @BeforeEach
    public void initialize() {
        // Initialize objects
        orderDTO = new OrderDTO();
        updateBookDTO = new UpdateOrderDTO();
        orderDTO.setIsbn("isbn test");
        orderDTO.setAuthor("author test");
        orderDTO.setTitle("title test");
        updateBookDTO.setId(bookId);
        updateBookDTO.setIsbn("isbn test");
        updateBookDTO.setAuthor("author test");
        updateBookDTO.setTitle("title test");
        orderList = new ArrayList<>();
        orderList.add(new Order());
        doReturn(orderList.stream()).when(bookPage).stream();

        // Instantiate test target and spy
        target = new OrderServiceImplementation(
                orderRepositoryMock,
                bookMapperMock
        );
        orderServiceImplementationSpy = spy(target);
    }

    @Test
    @DisplayName("When asking for an book the test should retrieve an Book inside a list")
    void testGetBooks01() {
        //PREPARE
        doReturn(bookPage).when(orderRepositoryMock).findAll(anyLong(), any(PageRequest.class));

        //RUN & VERIFY
        OrderListResponse orderListResponse = target.getBooks(
                0L,
                0,
                10,
                "id",
                "DESC");

        assertEquals(orderListResponse.getPayload().size(), orderList.size(), "These objects should be equal");
        verify(orderRepositoryMock, times(1)).findAll(anyLong(), any(PageRequest.class));
        verify(bookMapperMock, times(1)).bookToDetailBookDTO(any(Order.class));
    }

    @Test
    @DisplayName("Should throw orderplannerServiceException with isbn duplicated when " +
            "findByIsbn returns an element")
    void testSaveBook01() {
        //PREPARE
        doReturn(List.of(orderMock)).when(orderRepositoryMock).findByIsbn(anyString());

        //RUN
        OrderPlannerServiceException orderPlannerServiceException = assertThrows(
                OrderPlannerServiceException.class,
                () -> target.saveBook(orderDTO, username), "Should throw an orderplannerServiceException");

        //VERIFY
        verify(orderRepositoryMock, times(1)).findByIsbn(anyString());
        assertEquals(
                OrderPlannerResults.BOOK_CODE_DUPLICATED,
                orderPlannerServiceException.getOrderPlannerResults(), "These objects should be equal");
        assertNull(orderPlannerServiceException.getResultInfo(), "These objects should be null");
        assertEquals(OrderPlannerBaseResponse.class, orderPlannerServiceException.getResponseClass(), "These objects should be equal");
    }

    @Test
    @DisplayName("Should call save and setOperationResult with success")
    void testSaveBook02() {
        //PREPARE
        doReturn(List.of()).when(orderRepositoryMock)
                .findByIsbn(anyString());
        doReturn(orderMock).when(bookMapperMock)
                .bookDtoToBook(
                        orderDTOMock,
                        offsetDateTime,
                        offsetDateTime,
                        username);
        doReturn(new OrderPlannerBaseResponse()).when(orderServiceImplementationSpy)
                .setOperationResult(any(), any(), any());

        //RUN
        orderServiceImplementationSpy.saveBook(orderDTO, username);

        //VERIFY
        verify(orderRepositoryMock, times(1)).save(any());
        verify(orderServiceImplementationSpy, times(1))
                .setOperationResult(any(), any(), any());
    }

    @Test
    @DisplayName("Should throw orderplannerServiceException with element not found when findByIdAndDateDeleteIsNull " +
            "returns no elements")
    void testUpdateBook01() {
        //PREPARE
        doReturn(Optional.empty()).when(orderRepositoryMock).findById(bookId);

        //RUN
        OrderPlannerServiceException orderPlannerServiceException = assertThrows(
                OrderPlannerServiceException.class,
                () -> target.updateBook(updateBookDTO, username), "Should throw an orderplannerServiceException");

        //VERIFY
        assertEquals(OrderPlannerResults.BOOK_NOT_FOUND, orderPlannerServiceException.getOrderPlannerResults(), "These objects should be equal");
        assertNull(orderPlannerServiceException.getResultInfo(), "These objects should be null");
        assertEquals(OrderPlannerBaseResponse.class, orderPlannerServiceException.getResponseClass(), "These objects should be equal");
    }

    @Test
    @DisplayName("Should call findFirstByCodeAndDateDeleteIsNull, save and setOperationResult with success")
    void testUpdateBook03() {
        //PREPARE
        doReturn(Optional.of(orderMock)).when(orderRepositoryMock).findById(bookId);
        doReturn(bookId).when(orderMock).getId();
        doReturn(orderMock).when(bookMapperMock).updateBookDTOToBookEntity(
                any(),
                any(),
                any(),
                any(),
                any()
        );

        //RUN
        OrderPlannerBaseResponse result = orderServiceImplementationSpy.updateBook(
                updateBookDTO,
                username);

        //VERIFY
        assertEquals(0, result.getResultCode(), "These objects should be equal");
        verify(orderRepositoryMock, times(1)).findByIsbn(anyString());
        verify(orderRepositoryMock, times(1)).save(orderMock);
        verify(orderServiceImplementationSpy, times(1)).setOperationResult(
                any(),
                any(),
                any()
        );
    }

    @Test
    @DisplayName("Should call findFirstByCodeAndDateDeleteIsNull, save and setOperationResult with success")
    void testUpdateBook04() {
        //PREPARE
        doReturn(Optional.of(orderMock)).when(orderRepositoryMock).findById(bookId);
        doReturn(bookId).when(orderMock).getId();
        Order fromDb = new Order();
        fromDb.setId(bookId);
        doReturn(List.of(fromDb)).when(orderRepositoryMock).findByIsbn(anyString());
        doReturn(orderMock).when(bookMapperMock).updateBookDTOToBookEntity(
                any(),
                any(),
                any(),
                any(),
                any()
        );

        //RUN
        OrderPlannerBaseResponse result = orderServiceImplementationSpy.updateBook(
                updateBookDTO,
                username);

        //VERIFY
        assertEquals(0, result.getResultCode(), "These objects should be equal");
        verify(orderRepositoryMock, times(1)).findByIsbn(anyString());
        verify(orderRepositoryMock, times(1)).save(orderMock);
        verify(orderServiceImplementationSpy, times(1)).setOperationResult(
                any(),
                any(),
                any()
        );
    }
}
