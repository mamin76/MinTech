package com.eden.enforcementService.exception;

import com.eden.enforcementService.payload.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleApiRequestException(BusinessException businessException) {
        //
        String message = messageSource.getMessage(businessException.getMessage(),
                null, new Locale("en"));
        String ar_message = messageSource.getMessage(businessException.getMessage(),
                null, new Locale("ar"));
        //
        ApiException apiException = new ApiException(
                message,
                ar_message,
                businessException.getHttpStatus(),
                ZonedDateTime.now(ZoneId.of("Z")).toString()
        );
        //
        return new ResponseEntity<>(ApiResponse.builder().error(apiException).success(false)
                .code(businessException.getHttpStatus().value()).build(), businessException.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        String fieldError = ex.getFieldError().getField();
        String messageKey = ex.getFieldError(fieldError).getDefaultMessage();
        //
        String message = messageSource.getMessage(messageKey,
                null, new Locale("en"));
        String ar_message = messageSource.getMessage(messageKey,
                null, new Locale("ar"));
        //
        ApiException apiException = new ApiException(
                message,
                ar_message,
                status,
                ZonedDateTime.now(ZoneId.of("Z")).toString()
        );
        //
        return new ResponseEntity<>(ApiResponse.builder().error(apiException).success(false)
                .code(status.value()).build(), status);
    }
}
