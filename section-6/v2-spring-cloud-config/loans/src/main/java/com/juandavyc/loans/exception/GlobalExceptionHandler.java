package com.juandavyc.loans.exception;

import com.juandavyc.loans.dto.ErrorResponseDto;
import io.micrometer.core.instrument.distribution.TimeWindowPercentileHistogram;
import org.aspectj.bridge.IMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {

        Map<String, String> validationErrors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String errorField =  ((FieldError)error).getField();
            String errorMessage =  error.getDefaultMessage();
            validationErrors.put(errorField, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(validationErrors);
    }


    @ExceptionHandler(LoanAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleLoanAlreadyExistsException(
            LoanAlreadyExistsException exception,
            WebRequest webRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        getErrorResponseDto(
                                HttpStatus.CONFLICT,
                                exception.getMessage(),
                                webRequest
                        )
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        getErrorResponseDto(
                                HttpStatus.NOT_FOUND,
                                exception.getMessage(),
                                webRequest
                        )
                );
    }


    private ErrorResponseDto getErrorResponseDto(HttpStatus status, String message, WebRequest webRequest) {
        return new ErrorResponseDto(
                webRequest.getDescription(false),
                status,
                message,
                LocalDateTime.now()
        );
    }

}
