package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.applications.ReimbursementRequestApplicationService;
import com.reimbursement.health.domain.commands.reimbursement.CreateReimbursementRequestCommand;
import com.reimbursement.health.domain.dtos.*;
import com.reimbursement.health.domain.entities.ReimbursementRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reimbursement")
@AllArgsConstructor
public class ReimbursementRequestController {
    private final ReimbursementRequestApplicationService service;

    @PostMapping
    public ResponseEntity<ReimbursementRequest> create(@RequestBody CreateReimbursementRequestCommand command) {
        var companyId = service.create(command);
        return ResponseEntity.created(URI.create("/api/reimbursement/" + companyId)).body(companyId);
    }
    @GetMapping
    public List<ReimbursementRequestDto> findAll() {
        return service.findAll().stream().map(reimbursementRequest -> ReimbursementRequestDto.builder()
                .id(reimbursementRequest.getId().toString())
                .inclusionUser(reimbursementRequest.getInclusionUser())
                .updateDate(reimbursementRequest.getUpdateDate())
                .updateUser(reimbursementRequest.getUpdateUser())
                .inclusionDate(reimbursementRequest.getInclusionDate())
                .amount(reimbursementRequest.getAmount())
                .status(reimbursementRequest.getStatus().toString())
                .company(CompanyDto.toDto(reimbursementRequest.getCompany()))
                .user(UserDto.toDto(reimbursementRequest.getEmployee()))
                .invoice(InvoiceDto.toDto(reimbursementRequest.getInvoices()))
                .medical(MedicalDto.toDto(reimbursementRequest.getMedicalRequests()))
                .build()).toList();
    }
}
