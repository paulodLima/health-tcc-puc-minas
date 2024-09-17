package com.reimbursement.health.domain.commands.medical;

import com.reimbursement.health.domain.entities.ReimbursementRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Data
@Builder
public class CreateMedialRequestCommand {
    private ReimbursementRequest reimbursementRequest;
    private MultipartFile file;
}
