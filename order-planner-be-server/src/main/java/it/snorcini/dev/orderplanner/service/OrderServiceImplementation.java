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
import it.snorcini.dev.orderplanner.entity.QOrder;
import it.snorcini.dev.orderplanner.exception.OrderPlannerServiceException;
import it.snorcini.dev.orderplanner.mapper.OrderMapper;
import it.snorcini.dev.orderplanner.repository.DepotRepository;
import it.snorcini.dev.orderplanner.repository.OrderRepository;
import it.snorcini.dev.orderplanner.result.OrderPlannerResults;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
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
     * JPA Repository to manage Depot entity.
     */
    @NotNull
    private final DepotRepository depotRepository;

    /**
     * Mapper to convert from DTO to Entity
     * and vice versa.
     */
    @NotNull
    private final OrderMapper orderMapper;

    /**
     * Save a new Order.
     *
     * @param insertOrderDTO The new order to be saved
     * @return The new saved order containing the assigned id
     */
    @Override
    public OrderPlannerBaseResponse saveOrder(@Valid final OrderDTO insertOrderDTO)
            throws OrderPlannerServiceException {
        log.debug("OrderServiceImplementation.saveOrder[order = {}]", insertOrderDTO);
        Order order = orderMapper.orderDtoToOrder(insertOrderDTO);
        order.setStatus(OrderStatus.INITIAL);
        orderRepository.save(order);

        // 3. Return success
        return this.setOperationResult(new OrderPlannerBaseResponse(),
                OrderPlannerResults.OPERATION_SUCCESS, null);

    }

    /**
     * Update an Order.
     *
     * @param updateOrderDTO the new order values
     * @return the operation result
     */
    @Override
    public OrderPlannerBaseResponse updateOrder(@Valid final UpdateOrderDTO updateOrderDTO)
            throws OrderPlannerServiceException {
        log.debug("OrderServiceImplementation.updateOrder[order = {}]", updateOrderDTO);

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
        Order orderUpdated = orderMapper.updateOrderDTOToOrderEntity(updateOrderDTO, order.getUid());
        if (orderUpdated.getStatus() == null && order.getStatus() != null) {
            orderUpdated.setStatus(order.getStatus());
        }
        orderRepository.save(orderUpdated);
    }

    /**
     * Retrieve Orders.
     *
     * @return The response containing the order list
     */
    @Override
    public OrderListResponse getOrders(final OrderStatus status) throws OrderPlannerServiceException {
        log.debug("OrderServiceImplementation.getOrders[]");
        List<Order> orderList;
        if (status != null) {
            QOrder qOrder = new QOrder("order");
            Predicate predicate = qOrder.status.eq(status);
            orderList = (List<Order>) orderRepository.findAll(predicate);
        } else {
            orderList = orderRepository.findAll();
        }

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

    /**
     * Retrieve the delivery plan.
     *
     * @return The response containing the delivery path
     */
    @Override
    public PlanResponse startPlan(final String depotUid) throws OrderPlannerServiceException {
        log.debug("OrderServiceImplementation.startPlan[]");
        Depot start = depotRepository.findByUid(depotUid).orElseThrow(OrderPlannerServiceException::new);
        List<Order> orderList = orderRepository.findAll();
        List<Coordinate> plan = new ArrayList<>();
        plan.add(Coordinate.builder().latitude(start.getLatitude()).longitude(start.getLongitude()).build());
        orderList.forEach(order -> order.getPackages().forEach(aPackage -> plan.add(aPackage.getDestination())));
        plan.add(Coordinate.builder().latitude(start.getLatitude()).longitude(start.getLongitude()).build());
        return PlanResponse.builder().plan(plan).build();
    }
}
