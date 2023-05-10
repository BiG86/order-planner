package it.snorcini.dev.orderplanner.v1;

import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.OrderListResponse;
import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.entity.OrderStatus;
import it.snorcini.dev.orderplanner.exception.OrderPlannerServiceException;
import it.snorcini.dev.orderplanner.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/order")
@Slf4j
@AllArgsConstructor
public class OrderController {

    @NotNull
    private OrderService orderService;

    /**
     * Insert a single order.
     *
     * @param orderDTO   order data transfer object
     * @return operation result message
     */
    @PostMapping(value = "")
    public ResponseEntity<OrderPlannerBaseResponse> saveOrder(
            @Valid @RequestBody final OrderDTO orderDTO)
            throws OrderPlannerServiceException {
        log.debug("orderController.saveOrder[order = {}]", orderDTO);
        orderService.saveOrder(orderDTO);
        return new ResponseEntity<>(OrderPlannerBaseResponse.builder().resultMessage("Order taken over").build(),
                HttpStatus.OK);
    }

    /**
     * Update an order.
     *
     * @param updateOrderDTO the updated order data
     * @return the operation result
     */
    @PutMapping(value = "")
    public ResponseEntity<OrderPlannerBaseResponse> updateOrder(
            @Valid @RequestBody final UpdateOrderDTO updateOrderDTO)
            throws OrderPlannerServiceException {
        log.debug("orderController.updateOrder[application = {}]", updateOrderDTO);
        return new ResponseEntity<>(
                orderService.updateOrder(updateOrderDTO),
                HttpStatus.OK);
    }

    /**
     * Return the available orders.
     *
     * @return an orders list
     */
    @GetMapping(value = "")
    public ResponseEntity<OrderListResponse> getFilteredList(@RequestParam("status") final OrderStatus status)
            throws OrderPlannerServiceException {
        log.debug("orderController.getFilteredList[]");
        return new ResponseEntity<>(
                orderService.getOrders(status),
                HttpStatus.OK);
    }
}
