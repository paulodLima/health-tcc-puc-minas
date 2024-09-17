package com.reimbursement.health.domain.commands.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserCommand {
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private String perfil;
}
