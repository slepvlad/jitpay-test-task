package eu.jitpay.testtask.config;

import eu.jitpay.testtask.exceptions.BadRequestException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpErrorResponse handleException(BadRequestException e) {
        var status = HttpStatus.BAD_REQUEST;
        return HttpErrorResponse.builder()
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpErrorResponse handleException(SQLIntegrityConstraintViolationException e) {
        var status = HttpStatus.BAD_REQUEST;
        var message = "Constraint violation";
        return HttpErrorResponse.builder()
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpErrorResponse handleException(DataIntegrityViolationException e) {
        var status = HttpStatus.BAD_REQUEST;
        var message = "Constraint violation";
        return HttpErrorResponse.builder()
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .build();
    }

    @Builder
    @Getter
    @ToString
    public static class HttpErrorResponse {
        private final Long timestamp;
        private final int status;
        private final String error;
        private final String message;
    }
}
