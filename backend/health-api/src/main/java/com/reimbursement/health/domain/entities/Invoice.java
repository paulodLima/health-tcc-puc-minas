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
@Table(name = "TB_INVOICES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invoice extends AutidableEntity{
    @OneToOne
    @JoinColumn(name = "reimbursement_request_id", nullable = false)
    private ReimbursementRequest reimbursementRequest;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "s3_key", nullable = false)
    private String nameKey;

    @Builder
    public Invoice(String title, String inclusionUser, String fileUrl,ReimbursementRequest reimbursementRequest, String nameKey) {
        super(inclusionUser);
        setId(UUID.randomUUID());
        this.fileUrl = fileUrl;
        this.reimbursementRequest = reimbursementRequest;
        this.nameKey = nameKey;
    }
    public void update(String updateUser, String fileUrl) {
        this.updateDataAutidablec(updateUser);
        this.fileUrl = fileUrl;
    }
}
