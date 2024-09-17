package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.repositories.jpa.UserRepository;
import com.reimbursement.health.applications.service.EmailService;
import com.reimbursement.health.applications.service.KeycloackService;
import com.reimbursement.health.applications.service.token.KeycloakGeneratedToken;
import com.reimbursement.health.config.security.AuthenticationUtil;
import com.reimbursement.health.domain.commands.users.CreateUserCommand;
import com.reimbursement.health.domain.commands.users.UpdateUserCommand;
import com.reimbursement.health.domain.commands.users.UpdateUserPasswordCommand;
import com.reimbursement.health.domain.dtos.ApiResponseDto;
import com.reimbursement.health.domain.dtos.UserDto;
import com.reimbursement.health.domain.entities.User;
import com.reimbursement.health.domain.entities.UserBaseData;
import com.reimbursement.health.domain.records.GeneratedTokenRecord;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

        var userBase = repository.findAll();

        Map<String, UserBaseData> userBaseStatusMap = userBase.stream()
                .collect(Collectors.toMap(user -> user.getId().toString(),
                        user -> new UserBaseData(user.getStatus(), user.getName())));

        return users.stream()
                .filter(user -> userBaseStatusMap.containsKey(user.getId())) // Filtra usuários presentes em userBase
                .map(user -> {
                    UserBaseData userBaseData = userBaseStatusMap.get(user.getId());
                    return UserDto.builder()
                            .id(user.getId())
                            .name(userBaseData.getName())
                            .email(user.getEmail())
                            .login(user.getUsername())
                            .status(userBaseData.getStatus())
                            .inclusionDate(converterDate(user.getCreatedTimestamp()))
                            .build();
                })
                .toList();
    }

    private LocalDateTime converterDate(Long createdTimestamp) {
        var instant = Instant.ofEpochMilli(createdTimestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public UserDto findById(UUID id) {
        User user = repository.findById(id).orElseThrow();
        var userKeycloak = keycloackService.getUser(REALM, id);
        var role = (userKeycloak.getRealmRoles() != null && !userKeycloak.getRealmRoles().isEmpty())
               ? userKeycloak.getRealmRoles().getFirst()
               : "";

        return UserDto.builder()
                .id(userKeycloak.getId())
                .name(user.getName())
                .email(userKeycloak.getEmail())
                .login(userKeycloak.getUsername())
                .status(user.getStatus())
                .inclusionDate(converterDate(userKeycloak.getCreatedTimestamp()))
                .inclusionUser(user.getInclusionUser())
                .updateDate(user.getUpdateDate())
                .perfil(role)
                .status(user.getStatus())
                .updateUser(user.getUpdateUser())

                .build();

    }

    @Transactional
    public ResponseEntity<ApiResponseDto> create(CreateUserCommand command) {
        UUID userId = null;
        try {
            var firstAndLastName = command.getName().split(" ");
            var firstName = firstAndLastName[0];
            var lastName = firstAndLastName[firstAndLastName.length - 1];

            userId = keycloackService.createUser(REALM, command.getLogin(), firstName, lastName, command.getEmail(), command.getPassword(),command.getPerfil());

            var user = User.builder()
                    .id(userId)
                    .name(command.getName())
                    .login(command.getLogin())
                    .inclusionUser(AuthenticationUtil.getLogin())
                    .email(command.getEmail())
                    .build();

            repository.save(user);

            getTokenAndSentEmailCreated(command);

            return ResponseEntity.ok(new ApiResponseDto("Usuário criado com sucesso"));
        }
        catch (Exception e) {
            if (userId != null) {
                keycloackService.deleteUser(REALM, userId);
            }
            return ResponseEntity.internalServerError().body(new ApiResponseDto("Erro ao criar o usuario"));
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
        if (command.getEmail() != null) {
            getTokenAndSentEmailReset(CreateUserCommand.builder()
                    .email(command.getEmail())
                    .password(command.getPassword())
                    .name(command.getLogin())
                    .login(command.getLogin())
                    .build());
        }
    }

    public String generatedToken(GeneratedTokenRecord record) {
        return generatedToken(record.login(),record.password());
    }

    public void getTokenAndSentEmailCreated(CreateUserCommand command) {
        var token = generatedToken(GeneratedTokenRecord.builder()
                .login(command.getLogin())
                .password(command.getPassword())
                .build());

        emailService.sendResetPasswordCreatedMessage(command.getEmail(),token,command.getName());
    }

    public void getTokenAndSentEmailReset(CreateUserCommand command) {
        var token = generatedToken(GeneratedTokenRecord.builder()
                .login(command.getLogin())
                .password(command.getPassword())
                .build());

        emailService.sendResetPasswordMessage(command.getEmail(),token,command.getName());
    }
}
