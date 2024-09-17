package com.reimbursement.health.domain.commands.reimbursement;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CreateReimbursementRequestCommand {
    private UUID user;
    private UUID company;
    private String inclusionUser;
    private BigDecimal amount;
    private MultipartFile medicalUrl;
    private MultipartFile invoiceUrl;
}
