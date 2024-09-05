package com.reimbursement.health.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TB_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AutidableEntity{

    private String username;
    private String password;
    private String email;
    private Boolean status;
    @Column(name = "is_password_temporary")
    private Boolean isPasswordTemporary;

    @Builder
    public User(String username, String password, String email,String inclusionUser) {
        super(inclusionUser);
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = Boolean.TRUE;
        this.isPasswordTemporary = Boolean.TRUE;
    }

    public void update(String username, String password, String email, Boolean isPasswordTemporary, Boolean status, String updateUser) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isPasswordTemporary = isPasswordTemporary;
        this.status = status;
        updateDataAutidablec(updateUser);
    }

    public void passwordUpdate(String username, String password, String email, Boolean isPasswordTemporary) {
        this.password = password;
        this.isPasswordTemporary = isPasswordTemporary;
    }
}
