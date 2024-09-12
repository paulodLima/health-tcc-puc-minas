package com.reimbursement.health.domain.entities;

import com.reimbursement.health.domain.entities.valueobjects.CNPJ;
import com.reimbursement.health.domain.enuns.ReimbursementStatus;
import com.reimbursement.health.domain.enuns.convert.ReimbursementStatusConverter;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tb_reimbursement_requests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReimbursementRequest extends AutidableEntity{
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Convert(converter = ReimbursementStatusConverter.class)
    private ReimbursementStatus status;

    @OneToMany(mappedBy = "reimbursementRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "reimbursementRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalRequest> medicalRequests;

    @Builder
    public ReimbursementRequest(String inclusionUser,BigDecimal amount,Company company,User employee,ReimbursementStatus status) {
        super(inclusionUser);
        setId(UUID.randomUUID());
        this.amount = amount;
        this.company = company;
        this.employee = employee;
        this.status = status;
    }

    public void update(String updateUser,BigDecimal amount,Company company,User employee,ReimbursementStatus status) {
        this.updateDataAutidablec(updateUser);
        this.amount = amount;
        this.company = company;
        this.employee = employee;
        this.status = status;
    }

}
