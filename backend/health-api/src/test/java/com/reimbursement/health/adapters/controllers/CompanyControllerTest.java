package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.adapters.repositories.jpa.CompanyRepository;
import com.reimbursement.health.applications.CompanyApplicationService;
import com.reimbursement.health.applications.UserApplicationService;
import com.reimbursement.health.domain.entities.Company;
import com.reimbursement.health.domain.entities.valueobjects.CNPJ;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    @Mock
    private CompanyApplicationService service;

    @InjectMocks
    private CompanyController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    void findAll() throws Exception{
        Company company = Company.builder()
                .cnpj(new CNPJ("59863414000197"))
                .situation(true)
                .name("Test Company")
                .inclusionUser("paulo")
                .build();
        UUID companyId = UUID.randomUUID();
        when(service.findById(companyId)).thenReturn(company);

        mockMvc.perform(get("/api/company/".concat(companyId.toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}