package com.reimbursement.health.domain.dtos.response;

import com.reimbursement.health.domain.entities.Invoice;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class InvoiceResponseDto {
    private String fileUrl;

    public static List<InvoiceResponseDto> toDto(Set<Invoice> invoices) {
        if (invoices == null) return null;

        return invoices.stream()
                .map(invoice -> {
                    var dto = new InvoiceResponseDto();
                    dto.setFileUrl(invoice.getFileUrl());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
