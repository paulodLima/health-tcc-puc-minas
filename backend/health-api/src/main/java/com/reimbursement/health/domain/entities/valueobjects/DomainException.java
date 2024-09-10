package com.reimbursement.health.domain.entities.valueobjects;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DomainException extends RuntimeException {
    private HttpStatus httpStatus;
    public DomainException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
    public DomainException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
