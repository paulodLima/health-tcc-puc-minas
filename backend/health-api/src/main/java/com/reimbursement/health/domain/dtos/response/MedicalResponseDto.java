package com.reimbursement.health.domain.dtos.response;

import com.reimbursement.health.domain.entities.MedicalRequest;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class MedicalResponseDto {

    private String fileUrl;

    public static List<MedicalResponseDto> toDto(Set<MedicalRequest> medicalRequests) {
        if (medicalRequests == null) return null;

        return medicalRequests.stream()
                .map(medical -> {
                    var dto = new MedicalResponseDto();
                    dto.setFileUrl(medical.getFileUrl());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
