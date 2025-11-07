package com.job_hunt.application_service.config;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.job_hunt.application_service.dto.APIReponse;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to handle exceptions globally.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionhandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<APIReponse<Void>> handleValidationExceptions(
                        MethodArgumentNotValidException ex, WebRequest request) {
                Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
                                .collect(Collectors.toMap(error -> ((FieldError) error).getField(),
                                                error -> error.getDefaultMessage()));

                log.error("Validation error: {}", errors);

                APIReponse<Void> response = APIReponse.failure("Validation Failed", errors);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<APIReponse<Void>> handleRuntimeException(RuntimeException ex, WebRequest request) {
                log.error("Runtime exception: {}", ex.getMessage(), ex);
                APIReponse<Void> response = APIReponse.failure("Internal Server Error", ex.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // global exception handler
        @ExceptionHandler(Exception.class)
        public ResponseEntity<APIReponse<Void>> handleGlobalException(Exception ex, WebRequest request) {
                log.error("Exception: {}", ex.getMessage(), ex);
                APIReponse<Void> response = APIReponse.failure("Internal Server Error", ex.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
