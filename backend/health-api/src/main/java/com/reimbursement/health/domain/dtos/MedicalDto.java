package com.reimbursement.health.domain.dtos;

import com.reimbursement.health.domain.entities.Invoice;
import com.reimbursement.health.domain.entities.MedicalRequest;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class MedicalDto {

    private String fileUrl;
    private LocalDateTime inclusionDate;
    private String inclusionUser;

    public static List<MedicalDto> toDto(Set<MedicalRequest> medicalRequests) {
        if (medicalRequests == null) return null;

        return medicalRequests.stream()
                .map(medical -> {
                    var dto = new MedicalDto();
                    dto.setFileUrl(medical.getFileUrl());
                    dto.setInclusionDate(medical.getInclusionDate());
                    dto.setInclusionUser(medical.getInclusionUser());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
