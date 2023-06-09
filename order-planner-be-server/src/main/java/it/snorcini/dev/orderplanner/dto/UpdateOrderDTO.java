package it.snorcini.dev.orderplanner.dto;

import it.snorcini.dev.orderplanner.entity.OrderStatus;
import it.snorcini.dev.orderplanner.error.OrderPlannerValidationErrors;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * DTO used to update an Order.
 * <p>
 * Methods:
 * - constructor
 * - getter and setter
 * are auto-generated by Lombok.
 * <p>
 * This class @extends {@link OrderDTO}.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateOrderDTO extends OrderDTO {

    /**
     * Contains the value of primary key identify the item on DB.
     */
    @NotNull(message = OrderPlannerValidationErrors.ORDER_ID_NULL)
    protected String uid;

    protected OrderStatus status;
}
