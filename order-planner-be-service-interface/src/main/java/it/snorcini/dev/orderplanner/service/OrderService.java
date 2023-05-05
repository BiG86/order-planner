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
     * Save a new Book.
     *
     * @param orderDTO The new book to be saved
     * @param username     the validated user who sent the dto
     * @return Generic response orderplannerBaseResponse
     */
    OrderPlannerBaseResponse saveBook(@Valid OrderDTO orderDTO, String username)
            throws OrderPlannerServiceException;

    /**
     * Update an Book.
     *
     * @param updateBookDTO the new book values
     * @param username           the validated user who sent the dto
     * @return the operation result
     */
    OrderPlannerBaseResponse updateBook(@Valid UpdateOrderDTO updateBookDTO, String username)
            throws OrderPlannerServiceException;


    /**
     * Retrieve Books with optional filter.
     *
     * @param id             book id optional filter
     * @param page           pagination page number
     * @param size           pagination page size
     * @param orderBy        pagination order by
     * @param orderDirection pagination order direction
     * @return The response containing the application list
     */
    OrderListResponse getBooks(Long id,
                               Integer page,
                               Integer size,
                               String orderBy,
                               String orderDirection) throws OrderPlannerServiceException;

    /**
     * Delete a single book.
     *
     * @param id             book id to be deleted
     * @param lastUserModify The validated user who sent the request
     * @return The response containing the result
     */
    OrderPlannerBaseResponse deleteBook(Long id, String lastUserModify)
            throws OrderPlannerServiceException;

}
