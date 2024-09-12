package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.repositories.jpa.InvoiceRepository;
import com.reimbursement.health.adapters.repositories.jpa.ReimbursementRequestRepository;
import com.reimbursement.health.applications.service.S3Service;
import com.reimbursement.health.config.security.AuthenticationUtil;
import com.reimbursement.health.domain.commands.invoice.CreateInvoiceCommand;
import com.reimbursement.health.domain.entities.Invoice;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InvoiceApplicationService {
    private final S3Service s3Service;
    private final InvoiceRepository repository;
    private final ReimbursementRequestApplicationService requestApplicationService;

    public UUID create(CreateInvoiceCommand command) throws IOException {
        var url = s3Service.uploadFile(command.getFile().getOriginalFilename(),command.getFile().getInputStream(), command.getFile().getSize());
        var reimbursement = requestApplicationService.findById(command.getReimbursementRequest());

        var invoice = Invoice.builder()
                .inclusionUser(AuthenticationUtil.getLogin())
                .fileUrl(url)
                .reimbursementRequest(reimbursement)
                .build();

        return repository.save(invoice).getId();
    }

}
