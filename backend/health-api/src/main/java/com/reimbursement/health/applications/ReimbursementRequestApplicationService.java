package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.repositories.jpa.ReimbursementRequestRepository;
import com.reimbursement.health.domain.commands.reimbursement.CreateReimbursementRequestCommand;
import com.reimbursement.health.domain.entities.ReimbursementRequest;
import com.reimbursement.health.domain.enuns.ReimbursementStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReimbursementRequestApplicationService {
    private final ReimbursementRequestRepository repository;
    private final UserApplicationService userApplicationService;
    private final CompanyApplicationService companyApplicationService;

    public ReimbursementRequestApplicationService(ReimbursementRequestRepository repository, UserApplicationService userApplicationService, CompanyApplicationService companyApplicationService) {
        this.repository = repository;
        this.userApplicationService = userApplicationService;
        this.companyApplicationService = companyApplicationService;
    }

    public List<ReimbursementRequest> findAll() {return repository.findAll();}

    public ReimbursementRequest create(CreateReimbursementRequestCommand command) {
        var user = userApplicationService.findById(command.getUser());
        var company = companyApplicationService.findById(command.getCompany());
        var reimbursementStatus = converedStatus(command);

        var reimbursementRequest = ReimbursementRequest.builder()
                .inclusionUser(command.getInclusionUser())
                .company(company)
                .amount(command.getAmount())
                .employee(user)
                .status(reimbursementStatus)
                .build();
        return repository.save(reimbursementRequest);
    }

    private static ReimbursementStatus converedStatus(CreateReimbursementRequestCommand command) {
        return ReimbursementStatus.fromValue(command.getStatus());
    }
}
