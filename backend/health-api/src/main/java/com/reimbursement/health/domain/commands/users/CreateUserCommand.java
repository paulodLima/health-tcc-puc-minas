package com.reimbursement.health.domain.commands.users;

import lombok.Data;

@Data
public class CreateUserCommand {
    private String name;
    private String login;
    private String email;
}
