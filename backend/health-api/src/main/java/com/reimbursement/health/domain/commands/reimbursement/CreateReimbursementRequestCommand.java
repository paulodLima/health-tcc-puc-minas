package com.reimbursement.health.domain.commands.reimbursement;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateReimbursementRequestCommand {
    private UUID user;
    private UUID company;
    private String inclusionUser;
    private BigDecimal amount;
    private  Integer status;
}
