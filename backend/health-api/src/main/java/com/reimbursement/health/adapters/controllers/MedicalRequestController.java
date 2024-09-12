package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.applications.MedicalRequestApplicationService;
import com.reimbursement.health.domain.commands.medical.CreateMedialRequestCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/medical")
@AllArgsConstructor
public class MedicalRequestController {
    private final MedicalRequestApplicationService service;

    @PostMapping
    private ResponseEntity<UUID> createMedicalRequest(@ModelAttribute CreateMedialRequestCommand command) throws IOException {
        UUID uuid = service.create(command);
        return ResponseEntity.created(URI.create("/api/medical/" + uuid)).body(uuid);
    }
}

