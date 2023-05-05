package it.snorcini.dev.orderplanner.v1;

import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.OrderListResponse;
import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.service.OrderService;
import org.springframework.security.oauth2.jwt.Jwt;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OrderController.class})
@DisplayName("Tests regarding the BookController class")
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
    private UpdateOrderDTO updateBookDTOMock;

    public static final String PREFERRED_USERNAME = "preferred_username";

    private final String username = "usernameMock";
    private final Long bookId = 1L;

    @Test
    @DisplayName("Should call getFilteredList and return OK")
    void testGetFilteredList01() {
        // given
        doReturn(responseListMock).when(orderServiceMock).getBooks(bookId, null, null, null, null);

        // when
        ResponseEntity<OrderListResponse> response = target
                .getFilteredList(bookId, null, null, null, null);

        // then
        verify(orderServiceMock, times(1)).getBooks(bookId, null, null, null, null);
        assertNotNull(response.getBody(), "These objects should be not null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "These objects should be equal");
    }

    @Test
    @DisplayName("Should call saveBook and return OK")
    void testSaveBook01() {
        // given
        doReturn(responseMock).when(orderServiceMock).saveBook(orderDTOMock, username);

        // when
        ResponseEntity<OrderPlannerBaseResponse> response = target
                .saveBook(orderDTOMock, getJwtMock(username));

        // then
        verify(orderServiceMock, times(1)).saveBook(orderDTOMock, username);
        assertNotNull(response.getBody(), "These objects should be not null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "These objects should be equal");
    }

    @Test
    @DisplayName("Should call updateBook and return OK")
    void testUpdateBook01() {
        // given
        doReturn(responseMock).when(orderServiceMock).updateBook(updateBookDTOMock, username);

        // when
        ResponseEntity<OrderPlannerBaseResponse> response = target
                .updateBook(updateBookDTOMock, getJwtMock(username));

        // then
        verify(orderServiceMock, times(1)).updateBook(updateBookDTOMock, username);
        assertNotNull(response.getBody(), "These objects should be not null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "These objects should be equal");
    }

    @Test
    @DisplayName("Should call deleteBook and return OK")
    void testDeleteBook01() {
        // given
        doReturn(responseMock).when(orderServiceMock).deleteBook(bookId, username);

        // when
        ResponseEntity<OrderPlannerBaseResponse> response = target
                .deleteBook(bookId, getJwtMock(username));

        // then
        verify(orderServiceMock, times(1)).deleteBook(bookId, username);
        assertNotNull(response.getBody(), "These objects should be not null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "These objects should be equal");
    }

    public static Jwt getJwtMock(final String usernameMock) {
        Jwt jwtMock = mock(Jwt.class);
        doReturn(usernameMock).when(jwtMock).getClaimAsString(PREFERRED_USERNAME);
        return jwtMock;
    }
}
