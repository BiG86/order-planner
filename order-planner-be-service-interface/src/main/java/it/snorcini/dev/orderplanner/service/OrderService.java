package it.snorcini.dev.orderplanner.service;

import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.OrderListResponse;
import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.exception.OrderPlannerServiceException;
import jakarta.validation.Valid;

/**
 * Service interface to manage Book data.
 */
public interface OrderService {


    /**
     * Save a new Order.
     *
     * @param orderDTO The new order to be saved
     * @return Generic response orderplannerBaseResponse
     */
    OrderPlannerBaseResponse saveOrder(@Valid OrderDTO orderDTO)
            throws OrderPlannerServiceException;

    /**
     * Update an Order.
     *
     * @param updateOrderDTO the new order values
     * @return the operation result
     */
    OrderPlannerBaseResponse updateOrder(@Valid UpdateOrderDTO updateOrderDTO)
            throws OrderPlannerServiceException;


    /**
     * Retrieve Books with optional filter.
     *
     * @return The response containing the order list
     */
    OrderListResponse getOrders() throws OrderPlannerServiceException;

}
