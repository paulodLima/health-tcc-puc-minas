package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.controllers.MedicalRequestController;
import com.reimbursement.health.adapters.repositories.jpa.MedicalRequestRepository;
import com.reimbursement.health.applications.service.S3Service;
import com.reimbursement.health.config.security.AuthenticationUtil;
import com.reimbursement.health.domain.commands.medical.CreateMedialRequestCommand;
import com.reimbursement.health.domain.entities.MedicalRequest;
import com.reimbursement.health.domain.entities.ReimbursementRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MedicalRequestApplicationService {
    private final S3Service s3Service;
    private final MedicalRequestRepository repository;

    public UUID create(CreateMedialRequestCommand command) throws IOException {
        var url = s3Service.uploadFile(command.getFile().getOriginalFilename(),command.getFile().getInputStream(),command.getFile().getSize());

        var medical = MedicalRequest.builder()
                .fileUrl(url)
                .reimbursementRequest(command.getReimbursementRequest())
                .inclusionUser(AuthenticationUtil.getLogin())
                .nameKey(command.getFile().getOriginalFilename())
                .build();

        return repository.save(medical).getId();
    }

    public void update(ReimbursementRequest reimbursementRequest, MultipartFile file) throws IOException {
        var medical = repository.findById(reimbursementRequest.getMedicalRequests().getId()).orElseThrow();
        var url = s3Service.uploadFile(medical.getNameKey(), file.getInputStream(),file.getSize());

        medical.update(AuthenticationUtil.getLogin(),url);
        repository.save(medical);
    }

    public MedicalRequest findById(MedicalRequest medicalRequests) {
        return repository.findById(medicalRequests.getId()).orElseThrow();
    }
}
