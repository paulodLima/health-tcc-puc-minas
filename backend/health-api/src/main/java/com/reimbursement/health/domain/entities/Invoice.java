package com.reimbursement.health.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
