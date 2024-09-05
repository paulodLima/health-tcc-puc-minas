package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.applications.CompanyApplicationService;
import com.reimbursement.health.domain.commands.company.CreateCompanyCommand;
import com.reimbursement.health.domain.commands.company.StatusCompanyCommand;
import com.reimbursement.health.domain.commands.company.UpdateCompanyCommand;
import com.reimbursement.health.domain.commands.users.UpdateUserCommand;
import com.reimbursement.health.domain.dtos.CompanyDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyController {
    private final CompanyApplicationService service;

    @GetMapping
    public List<CompanyDto> findAll() {
        return service.findAll().stream().map(company -> CompanyDto.builder()
                .id(company.getId().toString())
                .name(company.getName())
                .status(company.getStatus())
                .cnpj(company.getCnpj().getNumeroFormatado())
                .inclusionDate(company.getInclusionDate())
                .updateDate(company.getUpdateDate())
                .inclusionUser(company.getInclusionUser())
                .updateUser(company.getUpdateUser())
                .build()
        ).toList();
    }

    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody CreateCompanyCommand command) {
        var companyId = service.create(command);
        return ResponseEntity.created(URI.create("/api/company/" + companyId)).body(companyId);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody UpdateCompanyCommand command) {
        if(!id.equals(command.getId())){
            throw new IllegalArgumentException("Id da url diferente do id do command");
        }
        service.update(command);
    }

    @PutMapping("/enable")
    public void ativar(@RequestBody StatusCompanyCommand command) {
        service.enable(command);
    }

    @PutMapping("/disable")
    public void desativar(@RequestBody StatusCompanyCommand command) {
        service.disable(command);
    }
}
