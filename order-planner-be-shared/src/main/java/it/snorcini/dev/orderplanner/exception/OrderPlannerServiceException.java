package it.snorcini.dev.orderplanner.exception;

import it.snorcini.dev.orderplanner.result.OrderPlannerResults;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This exception is throwable by any orderplanner service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlannerServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private OrderPlannerResults orderPlannerResults;
    private List<String> resultInfo;
    private Class responseClass;


    public OrderPlannerServiceException(final OrderPlannerResults orderPlannerResults,
                                        final List<String> resultInfo,
                                        final Class responseClass,
                                        final Exception e) {
        super(e);
        this.orderPlannerResults = orderPlannerResults;
        this.resultInfo = resultInfo;
        this.responseClass = responseClass;

    }


}
