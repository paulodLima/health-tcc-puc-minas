package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.applications.UserApplicationService;
import com.reimbursement.health.applications.service.EmailService;
import com.reimbursement.health.domain.commands.users.CreateUserCommand;
import com.reimbursement.health.domain.commands.users.UpdateUserCommand;
import com.reimbursement.health.domain.commands.users.UpdateUserPasswordCommand;
import com.reimbursement.health.domain.dtos.UserDto;
import com.reimbursement.health.domain.records.GeneratedTokenRecord;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserApplicationService service;

    @GetMapping
    public List<UserDto> listUser() {
        var users = service.findAll();
        return users.stream().map(user -> UserDto.builder()
                        .id(user.getId().toString())
                        .email(user.getEmail())
                        .status(user.getStatus())
                        .name(user.getName())
                        .login(user.getLogin())
                        .inclusionUser(user.getInclusionUser())
                        .inclusionDate(user.getInclusionDate())
                        .updateDate(user.getUpdateDate())
                        .updateUser(user.getUpdateUser())
                        .build())
                .toList();
    }
    @GetMapping("{id}")
    public UserDto listUser(@PathVariable UUID id) {
        var user = service.findById(id);
        return UserDto.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .status(user.getStatus())
                .name(user.getName())
                .login(user.getLogin())
                .inclusionUser(user.getInclusionUser())
                .inclusionDate(user.getInclusionDate())
                .updateDate(user.getUpdateDate())
                .updateUser(user.getUpdateUser())
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserCommand command) {
        return service.create(command);
    }

    @DeleteMapping("/{id}")
    public void createUser(@PathVariable UUID id) {service.delete(id);}

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody UpdateUserCommand command) {
        if(!id.equals(command.getId())){
            throw new IllegalArgumentException("Id da url diferente do id do command");
        }
        service.update(command);
    }

    @PutMapping("/reset-password")
    @CrossOrigin(origins = "*")
    public void update(@RequestBody UpdateUserPasswordCommand command) {
        service.updatePassword(command);
    }

    @PostMapping("/login")
    public String  KeycloakTokenGenerator(@RequestBody GeneratedTokenRecord record) {
        return service.generatedToken(record);
    }
}
