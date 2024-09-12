package com.reimbursement.health.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "TB_MEDICAL_REQUESTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicalRequest extends AutidableEntity{

    @ManyToOne
    @JoinColumn(name = "reimbursement_request_id", nullable = false)
    private ReimbursementRequest reimbursementRequest;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Builder
    public MedicalRequest(ReimbursementRequest reimbursementRequest, String fileUrl, String inclusionUser) {
        super(inclusionUser);
        setId(UUID.randomUUID());
        this.reimbursementRequest = reimbursementRequest;
        this.fileUrl = fileUrl;
    }
}
