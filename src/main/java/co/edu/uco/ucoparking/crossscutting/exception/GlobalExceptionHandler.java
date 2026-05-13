package co.edu.uco.ucoparking.crossscutting.exception;

import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ApiErrorResponseFactory apiErrorResponseFactory;

    public GlobalExceptionHandler(final ApiErrorResponseFactory apiErrorResponseFactory) {
        this.apiErrorResponseFactory = apiErrorResponseFactory;
    }

    @ExceptionHandler(UcoParkingException.class)
    public ResponseEntity<ApiErrorResponse> handleUcoParkingException(
            final UcoParkingException exception,
            final ServerWebExchange exchange) {
        return buildResponse(exception, exchange);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
            final Exception exception,
            final ServerWebExchange exchange) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiErrorResponseFactory.fromUnexpectedException(
                        exchange.getLocaleContext().getLocale()));
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            final UcoParkingException exception,
            final ServerWebExchange exchange) {
        final HttpStatus status = exception.hasCatalogMessageCode()
                ? exception.getMessageCode().getHttpStatus()
                : MessagesEnum.COMMON_UNEXPECTED_ERROR.getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(apiErrorResponseFactory.fromUcoParkingException(
                        exception,
                        exchange.getLocaleContext().getLocale()));
    }
}
