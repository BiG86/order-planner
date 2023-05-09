package it.snorcini.dev.orderplanner.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * This class provides information about pagination and is inherited by each *ListResponse class.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PageableListResponse extends OrderPlannerBaseResponse {

    /**
     * The total number of elements for the specific subclass entity, retrieved via a secondary count(*)
     * query automatically executed after the findAll method and populated in the Page object returned.
     */
    @PositiveOrZero
    @NotNull
    protected Long totalNumber;

}
