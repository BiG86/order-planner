package it.snorcini.dev.orderplanner.dto;

import it.snorcini.dev.orderplanner.error.OrderPlannerValidationErrors;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

/**
 * DTO used to update a Book.
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
    @NotNull(message = OrderPlannerValidationErrors.BOOK_ID_NULL)
    protected Long id;

    /**
     * ToString method.
     *
     * @return a string item representation
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append("UpdateBookDTO {\n")
                .append("    ").append(toIndentedString(super.toString())).append('\n')
                .append("    id: ").append(id).append('\n')
                .append('}').toString();
    }
}