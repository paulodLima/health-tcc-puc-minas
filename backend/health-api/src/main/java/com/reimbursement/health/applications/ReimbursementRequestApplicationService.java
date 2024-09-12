package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.repositories.jpa.ReimbursementRequestRepository;
import com.reimbursement.health.adapters.repositories.jpa.UserRepository;
import com.reimbursement.health.domain.commands.reimbursement.CreateReimbursementRequestCommand;
import com.reimbursement.health.domain.entities.ReimbursementRequest;
import com.reimbursement.health.domain.enuns.ReimbursementStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReimbursementRequestApplicationService {
    private final ReimbursementRequestRepository repository;
    private final UserRepository userRepository;
    private final CompanyApplicationService companyApplicationService;

    public ReimbursementRequestApplicationService(ReimbursementRequestRepository repository, UserRepository userRepository, CompanyApplicationService companyApplicationService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.companyApplicationService = companyApplicationService;
    }

    public List<ReimbursementRequest> findAll() {return repository.findAll();}

    public ReimbursementRequest create(CreateReimbursementRequestCommand command) {
        var user = userRepository.findById(command.getUser()).orElseThrow();
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

    ReimbursementRequest findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    private static ReimbursementStatus converedStatus(CreateReimbursementRequestCommand command) {
        return ReimbursementStatus.fromValue(command.getStatus());
    }
}
