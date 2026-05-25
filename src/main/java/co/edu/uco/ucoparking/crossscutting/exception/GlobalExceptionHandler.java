package co.edu.uco.ucoparking.crossscutting.exception;

import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ApiErrorResponseFactory apiErrorResponseFactory;

    public GlobalExceptionHandler(final ApiErrorResponseFactory apiErrorResponseFactory) {
        this.apiErrorResponseFactory = apiErrorResponseFactory;
    }

    @ExceptionHandler(UcoParkingException.class)
    public ResponseEntity<ApiErrorResponse> handleUcoParkingException(final UcoParkingException exception) {
        return buildResponse(exception);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(apiErrorResponseFactory.fromUcoParkingException(
                        UcoParkingException.of(MessagesEnum.COMMON_UNAUTHORIZED_API),
                        LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation() {
        return ResponseEntity
                .status(MessagesEnum.COMMON_INVALID_REQUEST.getHttpStatus())
                .body(apiErrorResponseFactory.fromUcoParkingException(
                        UcoParkingException.of(MessagesEnum.COMMON_INVALID_REQUEST),
                        LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(final Exception exception) {
        log.error("Unexpected error", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiErrorResponseFactory.fromUnexpectedException(LocaleContextHolder.getLocale()));
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(final UcoParkingException exception) {
        final HttpStatus status = exception.hasCatalogMessageCode()
                ? exception.getMessageCode().getHttpStatus()
                : MessagesEnum.COMMON_UNEXPECTED_ERROR.getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(apiErrorResponseFactory.fromUcoParkingException(
                        exception,
                        LocaleContextHolder.getLocale()));
    }
}
