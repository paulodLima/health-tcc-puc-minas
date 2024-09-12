package com.reimbursement.health.domain.commands.invoice;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class CreateInvoiceCommand {
    private UUID reimbursementRequest;
    private MultipartFile file;
    private String fileUrl;
}
