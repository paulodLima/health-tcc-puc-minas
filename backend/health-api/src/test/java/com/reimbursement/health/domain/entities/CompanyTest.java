package com.reimbursement.health.domain.entities;

import com.reimbursement.health.domain.entities.valueobjects.CNPJ;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void builder() {
        Company company = Company.builder()
                .cnpj(new CNPJ("59863414000197"))
                .situation(true)
                .name("teste")
                .inclusionUser("paulo")
                .build();
        Assert.notNull(company,"company not found");
    }

    @Test
    void update() {
        Company company = new Company();
        String newName = "New Company Name";
        CNPJ newCnpj = new CNPJ("59863414000197");
        Boolean newStatus = true;
        String newUpdateUser = "testUser";

        company.update(newName, newCnpj, newStatus, newUpdateUser);

        assertEquals(newName, company.getName());
        assertEquals(newCnpj, company.getCnpj());
        assertEquals(newStatus, company.getStatus());
        assertEquals(newUpdateUser, company.getUpdateUser());
    }

    @Test
    void disable() {
        Company company = Company.builder()
                .cnpj(new CNPJ("59863414000197"))
                .situation(true)
                .name("teste")
                .inclusionUser("paulo")
                .build();
        assertTrue(company.getStatus(), "Status should be true");

        company.disable("testDisable");

        assertFalse(company.getStatus(), "Status should be false after disable");
    }
    @Test
    void enable() {
        Company company = Company.builder()
                .cnpj(new CNPJ("59863414000197"))
                .situation(false)
                .name("teste")
                .inclusionUser("paulo")
                .build();

        assertFalse(company.getStatus(), "Status should be false");

        company.enable("testDisable");

        assertTrue(company.getStatus(), "Status should be true after enable");
    }
}