package com.reimbursement.health.domain.dtos;

import com.reimbursement.health.domain.entities.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {
    private String id;
    private String cnpj;
    private String name;
    private Boolean status;
    private LocalDateTime inclusionDate;
    private String inclusionUser;
    private LocalDateTime updateDate;
    private String updateUser;

    public static CompanyDto toDto(Company company) {
        if (company == null) return null;
        var dto = new CompanyDto();
        dto.setId(company.getId().toString());
        dto.setName(company.getName());
        dto.setCnpj(company.getCnpj().getNumeroFormatado());
        dto.setStatus(company.getStatus());
        dto.setInclusionDate(company.getInclusionDate());
        dto.setInclusionUser(company.getInclusionUser());
        dto.setUpdateDate(company.getUpdateDate());
        dto.setUpdateUser(company.getUpdateUser());
        return dto;
    }
}
