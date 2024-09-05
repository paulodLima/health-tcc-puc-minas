package com.reimbursement.health.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReimbursementRequestDto {
    private String id;
    private CompanyDto company;
    private UserDto user;
    private LocalDateTime inclusionDate;
    private String inclusionUser;
    private LocalDateTime updateDate;
    private String updateUser;
    private BigDecimal amount;
    private String status;
}
