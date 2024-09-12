package com.reimbursement.health.domain.commands.medical;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Data
public class CreateMedialRequestCommand {
    private UUID reimbursementRequest;
    private MultipartFile file;
    private String fileUrl;
}
