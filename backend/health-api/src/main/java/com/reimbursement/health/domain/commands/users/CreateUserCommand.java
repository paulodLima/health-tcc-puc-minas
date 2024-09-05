package com.reimbursement.health.domain.commands.users;

import lombok.Data;

@Data
public class CreateUserCommand {
    private String username;
    private String inclusionUser;
    private String password;
    private String email;
}
