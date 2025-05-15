package ru.yandex.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ErrorResponse notFoundHandler(final ProductNotFoundException e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse notAuthorizedHandler(final NotAuthorizedUserException e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse noProductInCartHandler(final NoProductsInShoppingCartException e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse productAlreadyInWarehouse(final SpecifiedProductAlreadyInWarehouseException e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse productLowQuantityInWarehouse(final ProductInShoppingCartLowQuantityInWarehouse e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse productNoFoundInWarehouse(final NoSpecifiedProductInWarehouseException e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse NoOrderFoundHandler(final NoOrderFoundException e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse NoInfoForCalculationHandler(final NotEnoughInfoInOrderToCalculateException e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse NoDeliveryHandler(final NoDeliveryFoundException e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse serviceUnavailableHandler(final ServiceUnavailableException e) {
        log.error(e.getMessage());
        return ErrorResponse.create(e, HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }
}
