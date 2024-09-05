package com.reimbursement.health.domain.commands.company;

import lombok.Data;

@Data
public class CreateCompanyCommand {
    private String cnpj;
    private String name;
    private Boolean status;
    private String inclusionUser;
}
