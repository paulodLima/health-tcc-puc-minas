package com.reimbursement.health.domain.commands.users;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateUserCommand {
    private UUID id;
    private String name;
    private String login;
    private String updateUser;
    private String password;
    private String email;
    private Boolean status;
    private Boolean isPasswordTemporary;
}
