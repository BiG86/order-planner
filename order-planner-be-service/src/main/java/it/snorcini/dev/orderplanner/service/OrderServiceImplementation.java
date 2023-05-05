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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service that computes data and
 * provides CRUD operations for the Book table
 * through the JPA repository.
 */
@Service
@Transactional
@Validated
@Slf4j
@AllArgsConstructor
public class OrderServiceImplementation extends BaseService implements OrderService {

    /**
     * JPA Repository to manage Book entity.
     */
    @NotNull
    private final OrderRepository orderRepository;

    /**
     * Mapper to convert from DTO to Entity
     * and vice versa.
     */
    @NotNull
    private final OrderMapper bookMapper;

    /**
     * Save a new Book.
     *
     * @param insertOrderDTO The new book to be saved
     * @param username           the validated user who sent the dto
     * @return The new saved book containing the assigned id
     */
    @Override
    public OrderPlannerBaseResponse saveBook(@Valid final OrderDTO insertOrderDTO,
                                             final String username)
            throws OrderPlannerServiceException {
        log.debug("BookServiceImplementation.saveBook[book = {}]", insertOrderDTO);

        // 1. Code duplicates validation
        isbnDuplicateCheck(insertOrderDTO);

        // 2. Activities population and save
        OffsetDateTime date = OffsetDateTime.now();
        orderRepository.save(bookMapper.bookDtoToBook(
                insertOrderDTO, date, date, username));

        // 3. Return success
        return this.setOperationResult(new OrderPlannerBaseResponse(),
                OrderPlannerResults.OPERATION_SUCCESS, null);

    }

    /**
     * Update an Book.
     *
     * @param updateBookDTO the new book values
     * @param username           the validated user who sent the dto
     * @return the operation result
     */
    @Override
    public OrderPlannerBaseResponse updateBook(@Valid final UpdateOrderDTO updateBookDTO,
                                               final String username)
            throws OrderPlannerServiceException {
        log.debug("BookServiceImplementation.updateBook[book = {}]", updateBookDTO);

        // 1. Book existence check
        Optional.ofNullable(orderRepository
                .findById(updateBookDTO.getId())
                .orElseThrow(() -> new OrderPlannerServiceException(
                        OrderPlannerResults.BOOK_NOT_FOUND,
                        null,
                        OrderPlannerBaseResponse.class))).ifPresent((Order order) ->
                updateBook(updateBookDTO, username, order)
        );

        // 5. Return success
        return this.setOperationResult(new OrderPlannerBaseResponse(),
                OrderPlannerResults.OPERATION_SUCCESS, null);
    }

    private void updateBook(final UpdateOrderDTO updateBookDTO
            , final String username, final Order order) {
        // 3. code duplication check
        isbnDuplicateCheck(updateBookDTO);
        // 4. update book
        OffsetDateTime date = OffsetDateTime.now();
        orderRepository.save(bookMapper.updateBookDTOToBookEntity(
                updateBookDTO,
                order.getId(),
                order.getDateInsert(),
                date,
                username));
    }

    /**
     * Determine if a DTO can be inserted/updated.
     *
     * @param orderDTO book DTO to be checked for insert/update operations
     */
    protected void isbnDuplicateCheck(@Valid final OrderDTO orderDTO) {
        orderRepository.findByIsbn(orderDTO.getIsbn())
                .stream().filter(
                (Order order) -> orderDTO.getClass().equals(OrderDTO.class)
                        || !order.getId().equals(((UpdateOrderDTO) orderDTO).getId())
        ).findAny().ifPresent((Order order) -> {
            throw new OrderPlannerServiceException(OrderPlannerResults.BOOK_CODE_DUPLICATED, null,
                    OrderPlannerBaseResponse.class);
        });
    }

    /**
     * Retrieve Books with optional filter.
     *
     * @param id             book id optional filter
     * @param page           pagination page number
     * @param size           pagination page size
     * @param orderBy        pagination order by
     * @param orderDirection pagination order direction
     * @return The response containing the book list
     */
    @Override
    public OrderListResponse getBooks(final Long id,
                                      final Integer page,
                                      final Integer size,
                                      final String orderBy,
                                      final String orderDirection) throws OrderPlannerServiceException {
        log.debug("BookServiceImplementation.getBooks[bookId = {}]", id);
        // 1. It retrieves books from DB optionally filtered by id, converts them into DTOs and return the
        // response object
        Page<Order> bookPage = orderRepository.findAll(
                id,
                this.buildPageable(page, size, orderBy, orderDirection)
        );
        OrderListResponse orderListResponse = new OrderListResponse(
                bookPage.getTotalElements(),
                bookPage
                        .stream()
                        .map(bookMapper::bookToDetailBookDTO)
                        .collect(Collectors.toList())
        );

        return (OrderListResponse) this.setOperationResult(
                orderListResponse,
                OrderPlannerResults.OPERATION_SUCCESS,
                null
        );
    }

    /**
     * Delete an book
     *
     * @param id             an {@link Order} id
     * @param lastUserModify the user from the authorization token
     * @return a {@link OrderPlannerBaseResponse}
     * @throws OrderPlannerServiceException exception thrown
     */
    @Override
    public OrderPlannerBaseResponse deleteBook(final Long id, final String lastUserModify)
            throws OrderPlannerServiceException {
        log.debug("BookServiceImplementation.deleteBook[id = {}]", id);
        orderRepository.deleteById(id);
        return this.setOperationResult(new OrderPlannerBaseResponse(),
                OrderPlannerResults.OPERATION_SUCCESS, null);
    }

}
