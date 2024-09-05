package com.reimbursement.health.domain.commands.company;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateCompanyCommand {
    private UUID id;
    private String cnpj;
    private String name;
    private Boolean status;
    private String updateUser;
}
