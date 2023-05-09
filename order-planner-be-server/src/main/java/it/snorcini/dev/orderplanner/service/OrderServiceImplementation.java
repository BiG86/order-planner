package it.snorcini.dev.orderplanner.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service that computes data and
 * provides CRUD operations for the Order table
 * through the JPA repository.
 */
@Service
@Transactional
@Validated
@Slf4j
@AllArgsConstructor
public class OrderServiceImplementation extends BaseService implements OrderService {

    /**
     * JPA Repository to manage Order entity.
     */
    @NotNull
    private final OrderRepository orderRepository;

    /**
     * Mapper to convert from DTO to Entity
     * and vice versa.
     */
    @NotNull
    private final OrderMapper orderMapper;

    /**
     * Save a new Order.
     *
     * @param insertOrderDTO The new book to be saved
     * @return The new saved order containing the assigned id
     */
    @Override
    public OrderPlannerBaseResponse saveOrder(@Valid final OrderDTO insertOrderDTO)
            throws OrderPlannerServiceException {
        log.debug("OrderServiceImplementation.saveOrder[book = {}]", insertOrderDTO);

        orderRepository.save(orderMapper.orderDtoToOrder(
                insertOrderDTO));

        // 3. Return success
        return this.setOperationResult(new OrderPlannerBaseResponse(),
                OrderPlannerResults.OPERATION_SUCCESS, null);

    }

    /**
     * Update an Order.
     *
     * @param updateOrderDTO the new book values
     * @return the operation result
     */
    @Override
    public OrderPlannerBaseResponse updateOrder(@Valid final UpdateOrderDTO updateOrderDTO)
            throws OrderPlannerServiceException {
        log.debug("OrderServiceImplementation.updateOrder[book = {}]", updateOrderDTO);

        // 1. Order existence check
        Optional.ofNullable(orderRepository
                .findByUid(updateOrderDTO.getUid())
                .orElseThrow(() -> new OrderPlannerServiceException(
                        OrderPlannerResults.ORDER_NOT_FOUND,
                        null,
                        OrderPlannerBaseResponse.class))).ifPresent((Order order) ->
                updateOrder(updateOrderDTO, order)
        );

        // 5. Return success
        return this.setOperationResult(new OrderPlannerBaseResponse(),
                OrderPlannerResults.OPERATION_SUCCESS, null);
    }

    private void updateOrder(final UpdateOrderDTO updateOrderDTO, final Order order) {
        // 4. update order
        orderRepository.save(orderMapper.updateOrderDTOToOrderEntity(
                updateOrderDTO,
                order.getUid()));
    }

    /**
     * Retrieve Orders with optional filter.
     *
     * @return The response containing the book list
     */
    @Override
    public OrderListResponse getOrders() throws OrderPlannerServiceException {
        log.debug("OrderServiceImplementation.getOrders[]");
        // 1. It retrieves books from DB optionally filtered by id, converts them into DTOs and return the
        // response object
        List<Order> orderList = orderRepository.findAll();
        OrderListResponse orderListResponse = OrderListResponse.builder().payload(orderList
                .stream()
                .map(orderMapper::orderToDetailOrderDTO)
                .collect(Collectors.toList())).build();

        return (OrderListResponse) this.setOperationResult(
                orderListResponse,
                OrderPlannerResults.OPERATION_SUCCESS,
                null
        );
    }
}
