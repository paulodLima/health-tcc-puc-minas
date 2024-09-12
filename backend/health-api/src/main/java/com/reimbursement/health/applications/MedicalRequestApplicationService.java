package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.controllers.MedicalRequestController;
import com.reimbursement.health.adapters.repositories.jpa.MedicalRequestRepository;
import com.reimbursement.health.applications.service.S3Service;
import com.reimbursement.health.config.security.AuthenticationUtil;
import com.reimbursement.health.domain.commands.medical.CreateMedialRequestCommand;
import com.reimbursement.health.domain.entities.MedicalRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MedicalRequestApplicationService {
    private final S3Service s3Service;
    private final MedicalRequestRepository repository;
    private final ReimbursementRequestApplicationService requestApplicationService;

    public UUID create(CreateMedialRequestCommand command) throws IOException {
        var url = s3Service.uploadFile(command.getFile().getOriginalFilename(),command.getFile().getInputStream(),command.getFile().getSize());
        var reimbursemen = requestApplicationService.findById(command.getReimbursementRequest());

        var medical = MedicalRequest.builder()
                .fileUrl(url)
                .reimbursementRequest(reimbursemen)
                .inclusionUser(AuthenticationUtil.getLogin())
                .build();

        return repository.save(medical).getId();
    }
}
