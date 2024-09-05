package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.repositories.jpa.CompanyRepository;
import com.reimbursement.health.domain.commands.company.CreateCompanyCommand;
import com.reimbursement.health.domain.commands.company.StatusCompanyCommand;
import com.reimbursement.health.domain.commands.company.UpdateCompanyCommand;
import com.reimbursement.health.domain.entities.Company;
import com.reimbursement.health.domain.entities.valueobjects.CNPJ;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyApplicationService {
    private final CompanyRepository repository;

    public CompanyApplicationService(CompanyRepository repository) {
        this.repository = repository;
    }

    public List<Company> findAll(){return repository.findAll();}

    public Company findById (UUID id){return repository.findById(id).orElseThrow();}

    public UUID create(CreateCompanyCommand command){
        var company = Company.builder()
                .inclusionUser(command.getInclusionUser())
                .name(command.getName())
                .cnpj(new CNPJ(command.getCnpj()))
                .situation(command.getStatus())
                .build();

        return repository.save(company).getId();
    }

    public void enable(StatusCompanyCommand command) {
        var company = repository.findById(command.getId()).orElseThrow();
        company.enable(command.getUpdateUserv());
        repository.save(company);
    }

    public void disable(StatusCompanyCommand command) {
        var company = repository.findById(command.getId()).orElseThrow();
        company.disable(command.getUpdateUserv());
        repository.save(company);
    }
    public void update(UpdateCompanyCommand command) {
        var company = repository.findById(command.getId()).orElseThrow();
        company.update(command.getName(),new CNPJ(command.getCnpj()),command.getStatus(),command.getUpdateUser());
        repository.save(company);
    }
}
