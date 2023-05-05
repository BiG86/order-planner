package it.snorcini.dev.orderplanner.exception;

import it.snorcini.dev.orderplanner.dto.OrderPlannerBaseResponse;
import it.snorcini.dev.orderplanner.result.OrderPlannerResults;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Centralized exceptions handler for the book manager.
 */
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handler for ConstraintViolationException.
     * Replies always with 400 Bad Request.
     *
     * @param exception the intercepted exception
     * @param request   web request
     * @return response entity
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleGenericError(final ConstraintViolationException exception,
                                                        final WebRequest request) {
        // 1. Returns the response object initialized with the error result code and result info
        logger.error("Validation failed: Bad request exception", exception);
        return instantiateResponse(exception,
                exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList()),
                request);
    }

    /**
     * Handler for orderplannerServiceException.
     * Replies always with 200 OK.
     *
     * @param exception the intercepted exception
     * @param request   web request
     * @return response entity
     */
    @ExceptionHandler(value = {OrderPlannerServiceException.class})
    protected ResponseEntity<Object> handleGenericError(final OrderPlannerServiceException exception,
                                                        final WebRequest request) throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        // 1. Returns the response object initialized with the error result code and result info
        logger.error("Managed exception has been caught", exception);
        return handleExceptionInternal(exception,
                exception.getResponseClass()
                        .getConstructor(Integer.class, String.class, List.class)
                        .newInstance(exception.getOrderPlannerResults().getResultCode(),
                                exception.getOrderPlannerResults().getResultMessage(),
                                exception.getResultInfo()),
                new HttpHeaders(),
                HttpStatus.OK,
                request);
    }

    /**
     * Instantiates Response from Exception, errorList and WebRequest.
     *
     * @param exception the handled exception
     * @param errorList the error list to be returned
     * @param request   the WebRequest
     * @return the instantiated orderplannerBaseResponse
     */
    private ResponseEntity<Object> instantiateResponse(final Exception exception,
                                                       final List<String> errorList,
                                                       final WebRequest request) {
        // 1. Instantiates new Response from Exception, the error List and the webRequest
        return handleExceptionInternal(exception,
                this.instantiateorderplannerBaseResponse(
                        OrderPlannerResults.INVALID_REQUEST,
                        errorList),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    /**
     * Instantiates new orderplannerBaseResponse.
     *
     * @param result     the operation result
     * @param resultInfo the result's detailed info
     * @return the instantiated orderplannerBaseResponse
     */
    protected OrderPlannerBaseResponse instantiateorderplannerBaseResponse(final OrderPlannerResults result,
                                                                          final List<String> resultInfo) {
        // 1. Instantiates new orderplannerBaseResponse from Result and resultInfo
        return new OrderPlannerBaseResponse(result.getResultCode(), result.getResultMessage(), resultInfo);
    }
}
