package com.reimbursement.health.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AutidableEntity extends Entity {
    @Column(name = "inclusion_date")
    private LocalDateTime inclusionDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "inclusion_user")
    private String inclusionUser;
    @Column(name = "update_user")
    private String updateUser;

    public AutidableEntity(String inclusionUser) {
        this.inclusionDate = LocalDateTime.now();
        this.inclusionUser =  inclusionUser;
    }

    protected void updateDataAutidablec(String updateUser){
        this.updateDate = LocalDateTime.now();
        this.updateUser = updateUser;
    }
}
