package com.reimbursement.health.domain.dtos.response;

import com.reimbursement.health.domain.entities.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponseDto {
    private String cnpj;
    private String name;
    private Boolean status;

    public static CompanyResponseDto toDto(Company company) {
        if (company == null) return null;
        var dto = new CompanyResponseDto();
        dto.setName(company.getName());
        dto.setCnpj(company.getCnpj().getNumeroFormatado());
        dto.setStatus(company.getStatus());
        return dto;
    }
}
