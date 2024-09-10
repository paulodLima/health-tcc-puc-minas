package com.reimbursement.health.domain.commands.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserCommand {
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
