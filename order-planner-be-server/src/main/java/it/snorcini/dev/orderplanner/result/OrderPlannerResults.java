package it.snorcini.dev.orderplanner.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for expected operations result.
 */
@Getter
@AllArgsConstructor
public enum OrderPlannerResults {

    OPERATION_SUCCESS(0, "Operation OK"),
    INVALID_REQUEST(-1, "The request is not valid"),
    //NOT_FOUND
    ORDER_NOT_FOUND(-2, "Order not found"),
    GENERIC_ERROR(-9999, "Generic error");

    private int resultCode;
    private String resultMessage;
}
