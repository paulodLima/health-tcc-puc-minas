package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.applications.ReimbursementRequestApplicationService;
import com.reimbursement.health.domain.commands.reimbursement.CreateReimbursementRequestCommand;
import com.reimbursement.health.domain.commands.reimbursement.UpdateReimbursementRequestCommand;
import com.reimbursement.health.domain.dtos.UserDto;
import com.reimbursement.health.domain.dtos.response.*;
import com.reimbursement.health.domain.entities.ReimbursementRequest;
import com.reimbursement.health.domain.records.UpdateStatusReimbursementRecord;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reimbursement")
@AllArgsConstructor
public class ReimbursementRequestController {
    private final ReimbursementRequestApplicationService service;

    @PostMapping
    public ResponseEntity<ReimbursementRequest> create(@RequestParam("user") UUID user,
                                                       @RequestParam("company") UUID company,
                                                       @RequestParam("amount") BigDecimal amount,
                                                       @RequestParam("medicalUrl") MultipartFile medicalUrl,
                                                       @RequestParam("invoiceUrl") MultipartFile invoiceUrl) throws IOException {

        var companyId = service.create(CreateReimbursementRequestCommand.builder()
                .amount(amount)
                .medicalUrl(medicalUrl)
                .invoiceUrl(invoiceUrl)
                .user(user)
                .company(company)
                .build());
        return ResponseEntity.created(URI.create("/api/reimbursement/" + companyId)).body(companyId);
    }

    @GetMapping
    public List<ReimbursementResponseDto> findAll() {
        return service.findAll().stream().map(reimbursementRequest -> ReimbursementResponseDto.builder()
                .id(reimbursementRequest.getId().toString())
                .inclusionDate(reimbursementRequest.getInclusionDate())
                .amount(reimbursementRequest.getAmount())
                .status(reimbursementRequest.getStatus().toString())
                .company(reimbursementRequest.getCompany().getName())
                .user(reimbursementRequest.getUser().getName())
                .invoiceUrl(reimbursementRequest.getInvoice().getFileUrl())
                .medicalUrl(reimbursementRequest.getMedicalRequests().getFileUrl())
                .observation(reimbursementRequest.getObservation())
                .build()).toList();
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<String> reimbursementStatus(@PathVariable UUID id, @RequestBody UpdateStatusReimbursementRecord record) {
        service.reimbursementStatus(id, record);
        return ResponseEntity.ok("Status atualizado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id,
                                         @RequestParam("user") UUID user,
                                         @RequestParam("company") UUID company,
                                         @RequestParam("amount") BigDecimal amount,
                                         @RequestParam("medicalUrl") MultipartFile medicalUrl,
                                         @RequestParam("invoiceUrl") MultipartFile invoiceUrl) throws IOException {


        service.update(id,  UpdateReimbursementRequestCommand.builder()
                .amount(amount)
                .medicalUrl(medicalUrl)
                .invoiceUrl(invoiceUrl)
                .user(user)
                .company(company).build());
        return ResponseEntity.ok("Status atualizado com sucesso!");
    }

    @GetMapping("find-user/{id}")
    public List<ReimbursementResponseDto> findByIdUser(@PathVariable UUID id) {
        return service.findByIdUser(id).stream().map(reimbursementRequest -> ReimbursementResponseDto.builder()
                .id(reimbursementRequest.getId().toString())
                .inclusionDate(reimbursementRequest.getInclusionDate())
                .amount(reimbursementRequest.getAmount())
                .status(reimbursementRequest.getStatus().toString())
                .company(reimbursementRequest.getCompany().getName())
                .user(reimbursementRequest.getUser().getName())
                .invoiceUrl(reimbursementRequest.getInvoice().getFileUrl())
                .medicalUrl(reimbursementRequest.getMedicalRequests().getFileUrl())
                .observation(reimbursementRequest.getObservation())
                .build()).toList();
    }

    @GetMapping("{id}")
    public ReimbursementResponseDto findById(@PathVariable UUID id) {
        var reimbursementRequest = service.findById(id);
        return ReimbursementResponseDto.builder()
                .id(reimbursementRequest.getId().toString())
                .inclusionDate(reimbursementRequest.getInclusionDate())
                .amount(reimbursementRequest.getAmount())
                .status(reimbursementRequest.getStatus().toString())
                .company(reimbursementRequest.getCompany().getName())
                .user(reimbursementRequest.getUser().getName())
                .invoiceUrl(reimbursementRequest.getInvoice().getFileUrl())
                .medicalUrl(reimbursementRequest.getMedicalRequests().getFileUrl())
                .observation(reimbursementRequest.getObservation())
                .build();
    }
}
