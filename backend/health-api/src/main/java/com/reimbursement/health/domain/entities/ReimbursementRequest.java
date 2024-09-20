package com.reimbursement.health.domain.entities;

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
    private String observation;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Convert(converter = ReimbursementStatusConverter.class)
    private ReimbursementStatus status;

    @OneToOne(mappedBy = "reimbursementRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Invoice invoice;

    @OneToOne(mappedBy = "reimbursementRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MedicalRequest medicalRequests;

    @Builder
    public ReimbursementRequest(String inclusionUser, BigDecimal amount, Company company, User user, ReimbursementStatus status) {
        super(inclusionUser);
        setId(UUID.randomUUID());
        this.amount = amount;
        this.company = company;
        this.user = user;
        this.status = status;
    }

    public void update(String updateUser,BigDecimal amount,Company company,User employee,String observation,ReimbursementStatus status) {
        this.updateDataAutidablec(updateUser);
        this.amount = amount;
        this.company = company;
        this.user = employee;
        this.observation = observation;
        this.status = status;
    }
    public void updateStatus(String updateUser,ReimbursementStatus status, String observation) {
        this.updateDataAutidablec(updateUser);
        this.status = status;
        this.observation = observation;
    }

}
