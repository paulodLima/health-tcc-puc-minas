package com.reimbursement.health.domain.commands.users;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateUserPasswordCommand {
    private UUID id;
    private String password;
}
