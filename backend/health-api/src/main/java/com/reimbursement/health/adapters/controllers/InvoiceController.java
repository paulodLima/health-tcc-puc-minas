package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.applications.InvoiceApplicationService;
import com.reimbursement.health.domain.commands.invoice.CreateInvoiceCommand;
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
@RequestMapping("/api/invoice")
@AllArgsConstructor
public class InvoiceController {
    private final InvoiceApplicationService service;

    @PostMapping
    private ResponseEntity<UUID> createIncoice(@ModelAttribute CreateInvoiceCommand command) throws IOException {
        UUID uuid = service.create(command);
        return ResponseEntity.created(URI.create("/api/invoice/" + uuid)).body(uuid);
    }
}
