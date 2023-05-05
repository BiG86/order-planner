package it.snorcini.dev.orderplanner.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.OrderListResponse;
import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.error.OrderPlannerValidationErrors;
import it.snorcini.dev.orderplanner.exception.OrderPlannerServiceException;
import it.snorcini.dev.orderplanner.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Validated
@RestController
@RequestMapping("/api/v1/book")
@Slf4j
@AllArgsConstructor
public class OrderController extends BaseController {

    @NotNull
    private OrderService orderService;

    /**
     * Insert a single book.
     *
     * @param orderDTO   book data transfer object
     * @param principal from the spring security context
     * @return operation result message
     */
    @PostMapping(value = "")
    @ApiOperation(value = "Save a new book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation completed", response = OrderPlannerBaseResponse.class),
            @ApiResponse(code = 400, message = "Malformed Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<OrderPlannerBaseResponse> saveBook(
            @Valid @RequestBody final OrderDTO orderDTO,
            @AuthenticationPrincipal final ClaimAccessor principal)
            throws OrderPlannerServiceException {
        log.debug("bookController.saveBook[book = {}]", orderDTO);

        return new ResponseEntity<>(
                orderService.saveBook(orderDTO, principal.getClaimAsString(PREFERRED_USERNAME)),
                HttpStatus.OK);
    }

    /**
     * Update a book if not already used by a config.
     *
     * @param updateBookDTO the updated book data
     * @param principal     from the spring security context
     * @return the operation result
     */
    @PutMapping(value = "")
    @ApiOperation(value = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation completed", response = OrderPlannerBaseResponse.class),
            @ApiResponse(code = 400, message = "Malformed Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<OrderPlannerBaseResponse> updateBook(
            @Valid @RequestBody final UpdateOrderDTO updateBookDTO,
            @AuthenticationPrincipal final ClaimAccessor principal)
            throws OrderPlannerServiceException {
        log.debug("bookController.updateBook[application = {}]", updateBookDTO);
        String username = principal.getClaimAsString(PREFERRED_USERNAME);
        return new ResponseEntity<>(
                orderService.updateBook(updateBookDTO, username),
                HttpStatus.OK);
    }

    /**
     * Return all books available.
     *
     * @param page           : number of the page to retrieve
     * @param size           : number of the items for each page
     * @param orderBy        : column name to use for sorting
     * @param orderDirection : ascending or descending
     * @return a list of book
     */
    @GetMapping(value = "")
    @ApiOperation(value = "Retrieve books optionally filtered by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation completed", response = OrderListResponse.class),
            @ApiResponse(code = 400, message = "Malformed Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<OrderListResponse> getFilteredList(
            final Long id,
            @PositiveOrZero(message = OrderPlannerValidationErrors.PAGE_LESS_THAN_ZERO) final Integer page,
            @Positive(message = OrderPlannerValidationErrors.SIZE_NOT_POSITIVE) final Integer size,
            @Size(max = 128,
                    message = OrderPlannerValidationErrors.ORDER_BY_INVALID) final String orderBy,
            @Size(max = 4,
                    message = OrderPlannerValidationErrors.ORDER_DIRECTION_INVALID) final String orderDirection)
            throws OrderPlannerServiceException {
        log.debug("bookController.getFilteredList[bookId = {}]", id);
        return new ResponseEntity<>(
                orderService.getBooks(id, page, size, orderBy, orderDirection),
                HttpStatus.OK);
    }

    /**
     * Delete a single book.
     *
     * @param id        the id of the book to delete
     * @param principal from the spring security context
     * @return operation result message
     */
    @DeleteMapping(value = "")
    @ApiOperation(value = "Delete book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation completed", response = OrderPlannerBaseResponse.class),
            @ApiResponse(code = 400, message = "Malformed request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<OrderPlannerBaseResponse> deleteBook(
            @Valid
            @NotNull(message = OrderPlannerValidationErrors.EMPTY_ID) final Long id,
            @AuthenticationPrincipal final ClaimAccessor principal)
            throws OrderPlannerServiceException {

        log.debug("bookController.deleteBook[id = {}]", id);
        return new ResponseEntity<>(orderService.deleteBook(
                id,
                principal.getClaimAsString(PREFERRED_USERNAME)), HttpStatus.OK);
    }

}
