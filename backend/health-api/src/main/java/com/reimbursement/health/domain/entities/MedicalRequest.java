package com.reimbursement.health.domain.entities;

import com.reimbursement.health.domain.enuns.ReimbursementStatus;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@Table(name = "TB_MEDICAL_REQUESTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicalRequest extends AutidableEntity{

    @OneToOne
    @JoinColumn(name = "reimbursement_request_id", nullable = false)
    private ReimbursementRequest reimbursementRequest;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "s3_key", nullable = false)
    private String nameKey;

    @Builder
    public MedicalRequest(ReimbursementRequest reimbursementRequest, String fileUrl, String inclusionUser,String nameKey) {
        super(inclusionUser);
        setId(UUID.randomUUID());
        this.reimbursementRequest = reimbursementRequest;
        this.fileUrl = fileUrl;
        this.nameKey = nameKey;
    }
    public void update(String updateUser, String fileUrl) {
        this.updateDataAutidablec(updateUser);
        this.fileUrl = fileUrl;
    }
}
