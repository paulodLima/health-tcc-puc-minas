package com.reimbursement.health.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiErrorsDTO {
    private List<String> errors;
}
