package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.applications.UserApplicationService;
import com.reimbursement.health.domain.commands.users.CreateUserCommand;
import com.reimbursement.health.domain.commands.users.UpdateUserCommand;
import com.reimbursement.health.domain.dtos.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserApplicationService service;

    @GetMapping
    public List<UserDto> listUser() {
        var users = service.findAll();
        return users.stream().map(user -> UserDto.builder()
                        .id(user.getId().toString())
                        .email(user.getEmail())
                        .status(user.getStatus())
                        .username(user.getUsername())
                        .password(user.getPassword())
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
                .username(user.getUsername())
                .password(user.getPassword())
                .inclusionUser(user.getInclusionUser())
                .inclusionDate(user.getInclusionDate())
                .updateDate(user.getUpdateDate())
                .updateUser(user.getUpdateUser())
                .build();
    }

    @PostMapping
    public ResponseEntity<UUID> createUser(@RequestBody CreateUserCommand command) {
        var userId = service.create(command);
        return ResponseEntity.created(URI.create("/api/user/" + userId)).body(userId);
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
}
