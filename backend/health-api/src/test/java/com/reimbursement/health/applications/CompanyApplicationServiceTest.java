package com.reimbursement.health.applications;

import com.reimbursement.health.config.security.AuthenticationUtil;
import com.reimbursement.health.domain.commands.company.CreateCompanyCommand;
import com.reimbursement.health.domain.commands.company.StatusCompanyCommand;
import com.reimbursement.health.domain.commands.company.UpdateCompanyCommand;
import com.reimbursement.health.domain.entities.Company;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class CompanyApplicationServiceTest {

    @Autowired
    private CompanyApplicationService service;

    @Test
    void findAllNotNull() {
        List<Company> list = service.findAll();
        assertNotNull(list);
    }

    @Test
    void findById() {
        var company = new CreateCompanyCommand();
        company.setCnpj("59863414000197");
        company.setName("company LTDA");
        company.setStatus(Boolean.TRUE);
        company.setInclusionUser("user inclusion");

        try (MockedStatic<AuthenticationUtil> mockedAuthUtil = mockStatic(AuthenticationUtil.class)) {
            mockedAuthUtil.when(AuthenticationUtil::getLogin).thenReturn("teste");
            UUID uuid = service.create(company);
            var byId = service.findById(uuid);
            assertNotNull(byId);
            assertEquals(company.getName(), byId.getName());
        }
    }

    @Test
    @Order(1)
    void create() {
        var user = new CreateCompanyCommand();
        user.setCnpj("59863414000197");
        user.setName("company LTDA");
        user.setStatus(Boolean.TRUE);
        user.setInclusionUser("user inclusion");

        try (MockedStatic<AuthenticationUtil> mockedAuthUtil = mockStatic(AuthenticationUtil.class)) {
            mockedAuthUtil.when(AuthenticationUtil::getLogin).thenReturn("teste");
            var id = service.create(user);
            assertNotNull(id);
        }
    }

    @Test
    @Order(2)
    void enable() {
        var user = new CreateCompanyCommand();
        user.setCnpj("59863414000197");
        user.setName("company LTDA");
        user.setStatus(Boolean.TRUE);
        user.setInclusionUser("user inclusion");
        try (MockedStatic<AuthenticationUtil> mockedAuthUtil = mockStatic(AuthenticationUtil.class)) {
            mockedAuthUtil.when(AuthenticationUtil::getLogin).thenReturn("teste");
            var id = service.create(user);

            var userEnable = new StatusCompanyCommand();
            userEnable.setId(id);
            userEnable.setUpdateUser("user update");

            service.enable(userEnable);

            var byId = service.findById(id);

            assertNotNull(byId);
            assertTrue(byId.getStatus());
        }
    }

    @Test
    void disable() {
        var company = new CreateCompanyCommand();
        company.setCnpj("59863414000197");
        company.setName("company LTDA");
        company.setStatus(Boolean.TRUE);
        company.setInclusionUser("company inclusion");
        try (MockedStatic<AuthenticationUtil> mockedAuthUtil = mockStatic(AuthenticationUtil.class)) {
            mockedAuthUtil.when(AuthenticationUtil::getLogin).thenReturn("teste");
            var id = service.create(company);

            var userEnable = new StatusCompanyCommand();
            userEnable.setId(id);
            userEnable.setUpdateUser("company update");

            service.disable(userEnable);

            var byId = service.findById(id);

            assertNotNull(byId);
            assertFalse(byId.getStatus());
        }
    }

    @Test
    void update() {
        var createCompanyCommand = new CreateCompanyCommand();
        createCompanyCommand.setCnpj("59863414000197");
        createCompanyCommand.setName("company LTDA");
        createCompanyCommand.setStatus(Boolean.TRUE);
        createCompanyCommand.setInclusionUser("user inclusion");
        try (MockedStatic<AuthenticationUtil> mockedAuthUtil = mockStatic(AuthenticationUtil.class)) {
            mockedAuthUtil.when(AuthenticationUtil::getLogin).thenReturn("teste");
            var id = service.create(createCompanyCommand);

            var updateCompanyCommand = new UpdateCompanyCommand();
            updateCompanyCommand.setId(id);
            updateCompanyCommand.setCnpj("18858694000168");
            updateCompanyCommand.setName("new LTDA");
            updateCompanyCommand.setStatus(Boolean.FALSE);
            updateCompanyCommand.setUpdateUser("user update");

            service.update(updateCompanyCommand);

            var byId = service.findById(id);

            assertNotNull(byId);
            assertFalse(byId.getStatus());
            assertEquals(updateCompanyCommand.getName(), byId.getName());
            assertNotEquals(updateCompanyCommand.getName(), createCompanyCommand.getName());
        }
    }
}