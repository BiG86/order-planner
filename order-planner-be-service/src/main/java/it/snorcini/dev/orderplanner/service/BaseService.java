package it.snorcini.dev.orderplanner.service;

import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.result.OrderPlannerResults;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Base Plugin implementing utility methods shared by plugins.
 * <p>
 * Contains the configuration for pagination of the list.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseService {

    @Value("${pagination.default.page-size}")
    private Integer defaultPageSize;
    @Value("${pagination.default.order-by}")
    private String defaultOrderBy;
    @Value("${pagination.default.order-direction}")
    private String defaultOrderDirection;

    /**
     * Populates base result info into the passed Response Object.
     *
     * @param baseResponse the response to be populated
     * @param results      the result to be written into the response
     * @param resultInfo  result message list containing dynamic contents
     * @return The populated response
     */
    protected OrderPlannerBaseResponse setOperationResult(@Valid final OrderPlannerBaseResponse baseResponse,
                                                          final OrderPlannerResults results,
                                                          final List<String> resultInfo) {
        baseResponse.setResultCode(results.getResultCode());
        baseResponse.setResultMessage(results.getResultMessage());
        baseResponse.setResultInfo(resultInfo);
        return baseResponse;
    }

    /**
     * Instantiates Pageable object to retrieve paged Data from Repository.
     *
     * @param page           the page number
     * @param size           the page size
     * @param orderBy        the properties to order the result
     * @param orderDirection the order direction
     * @return the Example object
     */
    protected Pageable buildPageable(final Integer page,
                                     final Integer size,
                                     final String orderBy,
                                     final String orderDirection) {
        // 1. Instantiates Pageable object and return it
        return PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(defaultPageSize),
                Sort.by(
                        Sort.Direction.valueOf(Optional.ofNullable(orderDirection)
                                .orElse(defaultOrderDirection)),
                        Optional.ofNullable(orderBy).orElse(defaultOrderBy)));
    }


}
