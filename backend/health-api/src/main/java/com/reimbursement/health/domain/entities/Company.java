package com.reimbursement.health.domain.entities;

import com.reimbursement.health.domain.entities.valueobjects.CNPJ;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_companies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends AutidableEntity {
    @Embedded
    private CNPJ cnpj;
    private String name;
    private Boolean status;

    @Builder
    public Company(String name, CNPJ cnpj, Boolean situation, String inclusionUser) {
        super(inclusionUser);
        this.name = name;
        this.cnpj = cnpj;
        this.status = situation;
    }

    public void update(String name, CNPJ cnpj, Boolean situation, String updateUser) {
        this.cnpj = cnpj;
        this.name = name;
        this.status = situation;
        this.updateDataAutidablec(updateUser);
    }

    public void disable(String usuario) {
        this.status = false;
        this.updateDataAutidablec(usuario);
    }

    public void enable(String usuario) {
        this.status = true;
        this.updateDataAutidablec(usuario);
    }
}
