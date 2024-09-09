package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.repositories.jpa.UserRepository;
import com.reimbursement.health.applications.service.EmailService;
import com.reimbursement.health.applications.service.KeycloackService;
import com.reimbursement.health.applications.service.token.KeycloakGeneratedToken;
import com.reimbursement.health.config.security.AuthenticationUtil;
import com.reimbursement.health.domain.commands.users.CreateUserCommand;
import com.reimbursement.health.domain.commands.users.UpdateUserCommand;
import com.reimbursement.health.domain.commands.users.UpdateUserPasswordCommand;
import com.reimbursement.health.domain.dtos.UserDto;
import com.reimbursement.health.domain.entities.User;
import com.reimbursement.health.domain.records.GeneratedTokenRecord;
import jakarta.transaction.Transactional;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class UserApplicationService extends KeycloakGeneratedToken {
    private final UserRepository repository;
    public static final String REALM = "health";
    private final KeycloackService keycloackService;
    private final EmailService emailService;

    public UserApplicationService(UserRepository repository, KeycloackService keycloackService, EmailService emailService) {
        this.repository = repository;
        this.keycloackService = keycloackService;
        this.emailService = emailService;
    }

    public List<UserDto> findAll() {
        var users = keycloackService.getUsers(REALM);

        return users.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .login(user.getUsername())
                .status(user.isEnabled())
                .inclusionDate(converterDate(user.getCreatedTimestamp()))
                .build()).toList();
    }

    private LocalDateTime converterDate(Long createdTimestamp) {
        var instant = Instant.ofEpochMilli(createdTimestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public UserDto findById(UUID id) {
        User user = repository.findById(id).orElseThrow();
        var userKeycloak = keycloackService.getUser(REALM, id);

        return UserDto.builder()
                .id(userKeycloak.getId())
                .name(user.getName())
                .email(userKeycloak.getEmail())
                .login(userKeycloak.getUsername())
                .status(user.getStatus())
                .inclusionDate(converterDate(userKeycloak.getCreatedTimestamp()))
                .inclusionUser(user.getInclusionUser())
                .updateDate(user.getUpdateDate())
                .updateUser(user.getUpdateUser())
                .build();

    }

    public ResponseEntity<String> create(CreateUserCommand command) {
        UUID userId = null;
        try {
            var firstAndLastName = command.getName().split(" ");
            var firstName = firstAndLastName[0];
            var lastName = firstAndLastName[firstAndLastName.length - 1];

            userId = keycloackService.createUser(REALM, command.getLogin(), firstName, lastName, command.getEmail(), command.getPassword());

            var user = User.builder()
                    .id(userId)
                    .name(command.getName())
                    .login(command.getLogin())
                    .inclusionUser(AuthenticationUtil.getLogin())
                    .email(command.getEmail())
                    .build();

            repository.save(user);

            getTokenAndSentEmail(command);

            return ResponseEntity.created(URI.create("/api/user/" + userId)).body(userId.toString());
        }
        catch (Exception e) {
            if (userId != null) {
                keycloackService.deleteUser(REALM, userId);
            }
            return ResponseEntity.internalServerError().body("Error while creating user");
        }
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public void update(UpdateUserCommand command) {
        var firstAndLastName = command.getName().split(" ");
        var firstName = firstAndLastName[0];
        var lastName = firstAndLastName[firstAndLastName.length - 1];

        keycloackService.editUser(command.getId(), REALM, firstName, lastName, command.getEmail());

        var user = repository.findById(command.getId()).orElseThrow();
        user.update(command.getName(), command.getLogin(), command.getEmail(), command.getIsPasswordTemporary(), command.getStatus(), AuthenticationUtil.getLogin());
        repository.save(user);
    }

    public void updatePassword(UpdateUserPasswordCommand command) {
        keycloackService.updatePasswordUser(command.getId(), REALM, command.getPassword());
    }

    public String generatedToken(GeneratedTokenRecord record) {
        return generatedToken(record.username(),record.password());
    }

    public void getTokenAndSentEmail(CreateUserCommand command) {
        var token = generatedToken(GeneratedTokenRecord.builder()
                .username(command.getLogin())
                .password(command.getPassword())
                .build());

        emailService.sendSimpleMessage(command.getEmail(),token,command.getName());
    }
}
