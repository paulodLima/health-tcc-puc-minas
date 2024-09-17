package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.repositories.jpa.ReimbursementRequestRepository;
import com.reimbursement.health.adapters.repositories.jpa.UserRepository;
import com.reimbursement.health.applications.service.EmailService;
import com.reimbursement.health.config.security.AuthenticationUtil;
import com.reimbursement.health.domain.commands.invoice.CreateInvoiceCommand;
import com.reimbursement.health.domain.commands.medical.CreateMedialRequestCommand;
import com.reimbursement.health.domain.commands.reimbursement.CreateReimbursementRequestCommand;
import com.reimbursement.health.domain.commands.reimbursement.UpdateReimbursementRequestCommand;
import com.reimbursement.health.domain.entities.MedicalRequest;
import com.reimbursement.health.domain.entities.ReimbursementRequest;
import com.reimbursement.health.domain.enuns.ReimbursementStatus;
import com.reimbursement.health.domain.records.UpdateStatusReimbursementRecord;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ReimbursementRequestApplicationService {
    private final ReimbursementRequestRepository repository;
    private final UserRepository userRepository;
    private final CompanyApplicationService companyApplicationService;
    private final EmailService emailService;
    private final InvoiceApplicationService invoiceService;
    private final MedicalRequestApplicationService medicalService;

    public ReimbursementRequestApplicationService(ReimbursementRequestRepository repository, UserRepository userRepository, CompanyApplicationService companyApplicationService, EmailService emailService, InvoiceApplicationService invoiceService, MedicalRequestApplicationService medicalService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.companyApplicationService = companyApplicationService;
        this.emailService = emailService;
        this.invoiceService = invoiceService;
        this.medicalService = medicalService;
    }

    public List<ReimbursementRequest> findAll() {
        return repository.findAll();
    }

    public ReimbursementRequest create(CreateReimbursementRequestCommand command) throws IOException {
        var user = userRepository.findById(command.getUser()).orElseThrow();
        var company = companyApplicationService.findById(command.getCompany());

        var reimbursementRequest = ReimbursementRequest.builder()
                .inclusionUser(AuthenticationUtil.getLogin())
                .company(company)
                .amount(command.getAmount())
                .user(user)
                .status(ReimbursementStatus.PENDING)
                .build();
        var request = repository.save(reimbursementRequest);

        createInvoice(reimbursementRequest, command.getInvoiceUrl());
        createMedical(reimbursementRequest, command.getMedicalUrl());
        return request;
    }

    private void createMedical(ReimbursementRequest reimbursementRequest, MultipartFile medical) throws IOException {
        medicalService.create(CreateMedialRequestCommand.builder()
                .file(medical)
                .reimbursementRequest(reimbursementRequest)
                .build());

    }

    private void createInvoice(ReimbursementRequest reimbursementRequest, MultipartFile invoice) throws IOException {
        invoiceService.create(CreateInvoiceCommand.builder()
                .file(invoice)
                .reimbursementRequest(reimbursementRequest)
                .build());
    }

    public ReimbursementRequest findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public List<ReimbursementRequest> findByIdUser(UUID id) {
        return repository.findByIdUser(id).stream().sorted(Comparator.comparing(ReimbursementRequest::getInclusionDate)).toList();
    }

    public void reimbursementStatus(UUID id, UpdateStatusReimbursementRecord record) {
        var reimbursement = repository.findById(id).orElseThrow(() -> new NotFoundException("Reembolso não encontrado com ID: " + id));
        var status = converedStatus(record.status());
        reimbursement.updateStatus(AuthenticationUtil.getLogin(), status, record.observation());
        repository.save(reimbursement);
        if (status.equals(ReimbursementStatus.REJECTED)) {
            emailService.sendMessageRejecterd(reimbursement.getUser().getEmail(), reimbursement.getUser().getName(), record.observation());
        } else {
            if (status.equals(ReimbursementStatus.APPROVED))
                emailService.sendMessageOk(reimbursement.getUser().getEmail(), reimbursement.getUser().getName());
        }
    }

    private static ReimbursementStatus converedStatus(Integer status) {
        return ReimbursementStatus.fromValue(status);
    }

    public void update(UUID id, UpdateReimbursementRequestCommand command) throws IOException {
        var reimbursement = repository.findById(id).orElseThrow(() -> new NotFoundException("Reembolso não encontrado com ID: " + id));
        var company = companyApplicationService.findById(command.getCompany());
        var user = userRepository.findById(command.getUser()).orElseThrow();

        reimbursement.update(AuthenticationUtil.getLogin(),command.getAmount(),company,user);
        updateInvoice(reimbursement,command.getInvoiceUrl());
        updateMedical(reimbursement,command.getMedicalUrl());
        repository.save(reimbursement);
    }

    private void updateMedical(ReimbursementRequest reimbursementRequest, MultipartFile file) throws IOException {
        medicalService.update(reimbursementRequest,file);
    }

    private void updateInvoice(ReimbursementRequest reimbursementRequest, MultipartFile file) throws IOException {
        invoiceService.update(reimbursementRequest,file);
    }
}
