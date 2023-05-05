package it.snorcini.dev.orderplanner.dto;

import it.snorcini.dev.orderplanner.error.OrderPlannerValidationErrors;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * DTO returned to the caller, contains a detail of a book.
 * <p>
 * Methods:
 * - constructor
 * - getter and setter
 * - equals
 * are auto-generated by Lombok.
 * <p>
 * This class @extends {@link UpdateOrderDTO}.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderDTO extends UpdateOrderDTO {


    /**
     * Contains the events that modified the object.
     *
     * @see ActivitiesDTO
     */
    @NotNull(message = OrderPlannerValidationErrors.ACTIVITIES_NULL)
    @Valid
    private ActivitiesDTO activities;

    /**
     * ToString method.
     *
     * @return a string item representation
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append("DetailBookDTO {\n")
                .append("    ").append(toIndentedString(super.toString())).append('\n')
                .append("    activities: ").append(toIndentedString(activities)).append('\n')
                .append('}').toString();
    }
}
