package com.reimbursement.health.applications;

import com.reimbursement.health.adapters.repositories.jpa.UserRepository;
import com.reimbursement.health.applications.service.KeycloackService;
import com.reimbursement.health.config.security.AuthenticationUtil;
import com.reimbursement.health.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserApplicationServiceTest {

    @Autowired
    private UserApplicationService service;
    @Autowired
    private UserRepository repository;
    private final UUID userId = UUID.randomUUID();

    @Mock
    KeycloackService keycloackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = User.builder()
                .id(userId)
                .inclusionUser("teste")
                .login("teste")
                .name("teste")
                .email("p@mail.com")
                .build();
        repository.save(user);
    }

    @Test
    void findAll() {
        var result = service.findAll();
        assertNotNull(result);
        assertEquals(1, result.size(), "O número de usuários deve ser 1");
    }

}