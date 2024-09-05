package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.repositories.jpa.UserRepository;
import com.reimbursement.health.domain.commands.users.CreateUserCommand;
import com.reimbursement.health.domain.commands.users.UpdateUserCommand;
import com.reimbursement.health.domain.dtos.UserDto;
import com.reimbursement.health.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserApplicationService {
    private final UserRepository repository;

    public UserApplicationService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow();

    }

    public UUID create(CreateUserCommand command) {
        var user = User.builder()
                .username(command.getUsername())
                .inclusionUser(command.getInclusionUser())
                .password(command.getPassword())
                .email(command.getEmail())
                .build();
        return repository.save(user).getId();
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public void update(UpdateUserCommand command) {
        var user = repository.findById(command.getId()).orElseThrow();
        user.update(command.getUsername(), command.getPassword(), command.getEmail(), command.getIsPasswordTemporary(), command.getStatus());
        repository.save(user);
    }
}
