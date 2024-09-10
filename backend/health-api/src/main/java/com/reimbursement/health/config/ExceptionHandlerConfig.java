package com.reimbursement.health.config;

import com.reimbursement.health.domain.dtos.ApiErrorsDTO;
import com.reimbursement.health.domain.entities.valueobjects.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerConfig {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiErrorsDTO> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(new ApiErrorsDTO(errors), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorsDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getAllErrors().stream().map(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            // Substitui o placeholder {field} pelo nome do campo
            if (errorMessage == null) return "";
            errorMessage = errorMessage.replace("{field}", fieldName);
            return  errorMessage;
        }).toList();

        return new ResponseEntity<>(new ApiErrorsDTO(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorsDTO> handleDomainExceptions(DomainException ex) {
        return new ResponseEntity<>(new ApiErrorsDTO(Collections.singletonList(ex.getMessage())), ex.getHttpStatus());
    }
}
