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
    @ManyToOne
    @JoinColumn(name = "reimbursement_request_id", nullable = false)
    private ReimbursementRequest reimbursementRequest;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Builder
    public Invoice(String title, String inclusionUser, String fileUrl,ReimbursementRequest reimbursementRequest) {
        super(inclusionUser);
        setId(UUID.randomUUID());
        this.fileUrl = fileUrl;
        this.reimbursementRequest = reimbursementRequest;
    }
}
