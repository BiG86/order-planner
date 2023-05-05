package it.snorcini.dev.orderplanner.error;

/**
 * Book manager Validation Error Codes complete list.
 */
public final class OrderPlannerValidationErrors {
    public static final String ORDER_DIRECTION_INVALID = "orderplanner.be.order.direction.invalid";
    public static final String ORDER_BY_INVALID = "orderplanner.be.order.by.invalid";
    public static final String PAGE_LESS_THAN_ZERO = "orderplanner.be.page.negative";
    public static final String SIZE_NOT_POSITIVE = "orderplanner.be.size.not.positive";
    public static final String EMPTY_ID = "orderplanner.be.id.empty";
    public static final String ISBN_NULL = "orderplanner.be.book.isbn.null";
    public static final String EMPTY_DATE_INSERT = "orderplanner.be.date.insert.empty";
    public static final String EMPTY_DATE_MODIFY = "orderplanner.be.date.modify.empty";
    public static final String EMPTY_LAST_USER_MODIFY = "orderplanner.be.lastusermodify.empty";
    public static final String LAST_USER_MODIFY_TOO_LONG = "orderplanner.lastusermodify.toolong";
    public static final String BOOK_ID_NULL = "orderplanner.be.book.id.null";
    public static final String TITLE_NULL = "orderplanner.be.book.title.null";
    public static final String AUTHOR_NULL = "orderplanner.be.book.author.null";
    public static final String ACTIVITIES_NULL = "orderplanner.be.activities.null";

    private OrderPlannerValidationErrors() {
    }
}
