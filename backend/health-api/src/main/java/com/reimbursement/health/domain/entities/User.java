package com.reimbursement.health.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "TB_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AutidableEntity{
    private String name;
    private String login;
    private String email;
    private Boolean status;
    @Column(name = "is_password_temporary")
    private Boolean isPasswordTemporary;

    @Builder
    public User(String name, String email, String inclusionUser,String login,UUID id) {
        super(inclusionUser);
        setId(id);
        this.name = name;
        this.login = login;
        this.email = email;
        this.status = Boolean.TRUE;
        this.isPasswordTemporary = Boolean.TRUE;
    }

    public void update(String username,String login, String email, Boolean isPasswordTemporary, Boolean status, String updateUser) {
        this.name = username;
        this.email = email;
        this.login = login;
        this.isPasswordTemporary = isPasswordTemporary;
        this.status = status;
        updateDataAutidablec(updateUser);
    }
}
