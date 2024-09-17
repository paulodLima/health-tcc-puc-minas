package com.reimbursement.health.domain.dtos.response;

import com.reimbursement.health.domain.dtos.CompanyDto;
import com.reimbursement.health.domain.dtos.InvoiceDto;
import com.reimbursement.health.domain.dtos.MedicalDto;
import com.reimbursement.health.domain.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReimbursementResponseDto {
    private String id;
    private String company;
    private String user;
    private LocalDateTime inclusionDate;
    private BigDecimal amount;
    private String status;
    private String medicalUrl;
    private String invoiceUrl;
    private String observation;
}
