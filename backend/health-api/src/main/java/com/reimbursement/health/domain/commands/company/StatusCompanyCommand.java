package com.reimbursement.health.domain.commands.company;

import lombok.Data;

import java.util.UUID;

@Data
public class StatusCompanyCommand {
    private UUID id;
    private String updateUserv;
}
