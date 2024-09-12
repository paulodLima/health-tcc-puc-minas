package com.reimbursement.health.domain.dtos;

import com.reimbursement.health.domain.entities.Invoice;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class InvoiceDto {
    private String fileUrl;
    private LocalDateTime inclusionDate;
    private String inclusionUser;

    public static List<InvoiceDto> toDto(Set<Invoice> invoices) {
        if (invoices == null) return null;

        return invoices.stream()
                .map(invoice -> {
                    var dto = new InvoiceDto();
                    dto.setFileUrl(invoice.getFileUrl());
                    dto.setInclusionDate(invoice.getInclusionDate());
                    dto.setInclusionUser(invoice.getInclusionUser());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
