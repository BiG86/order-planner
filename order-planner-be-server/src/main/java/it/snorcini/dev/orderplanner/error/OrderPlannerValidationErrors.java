package it.snorcini.dev.orderplanner.error;

/**
 * Order Planner Validation Error Codes complete list.
 */
public final class OrderPlannerValidationErrors {
    public static final String ORDERS_NULL = "orderplanner.be.order.orders.null";
    public static final String DESCRIPTION_NULL = "orderplanner.be.package.description.null";
    public static final String LATITUDE_NULL = "orderplanner.be.coordinate.latitude.null";
    public static final String LONGITUDE_NULL = "orderplanner.be.coordinate.longitude.null";
    public static final String COORDINATE_NULL = "orderplanner.be.coordinate.null";
    public static final String ORDER_ID_NULL = "orderplanner.be.order.id.null";

    private OrderPlannerValidationErrors() {
    }
}
