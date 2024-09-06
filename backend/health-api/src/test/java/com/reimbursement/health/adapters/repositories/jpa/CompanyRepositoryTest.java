package com.reimbursement.health.adapters.repositories.jpa;

import com.reimbursement.health.domain.entities.Company;
import com.reimbursement.health.domain.entities.valueobjects.CNPJ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testSaveAndFindCompany(){
        Company company = Company.builder()
                .cnpj(new CNPJ("59863414000197"))
                .situation(true)
                .name("Test Company")
                .inclusionUser("paulo")
                .build();

        Company savedCompany = companyRepository.save(company);
        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());

        assertThat(foundCompany).isPresent();
        assertThat(foundCompany.get().getName()).isEqualTo("Test Company");
        assertThat(foundCompany.get().getCnpj()).isEqualTo(new CNPJ("59863414000197"));
        assertThat(foundCompany.get().getStatus()).isTrue();
    }

    @Test
    public void testSaveAndUpdateCompany(){
        Company company = Company.builder()
                .cnpj(new CNPJ("59863414000197"))
                .situation(true)
                .name("Test Company")
                .inclusionUser("paulo")
                .build();

        Company savedCompany = companyRepository.save(company);
        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());

        assertThat(foundCompany).isPresent();

        String newName = "New Company Name";
        CNPJ newCnpj = new CNPJ("59863414000197");
        Boolean newStatus = false;
        String newUpdateUser = "testUser";

        foundCompany.get().update(newName, newCnpj, newStatus, newUpdateUser);

        companyRepository.save(foundCompany.get());

        Optional<Company> updatedCompany = companyRepository.findById(foundCompany.get().getId());

        assertThat(updatedCompany).isPresent();
        assertThat(updatedCompany.get().getName()).isEqualTo("New Company Name");
        assertThat(updatedCompany.get().getCnpj()).isEqualTo(new CNPJ("59863414000197"));
        assertFalse(updatedCompany.get().getStatus());
    }

    @Test
    public void testSaveAndDeleteCompany(){
        Company company = Company.builder()
                .cnpj(new CNPJ("59863414000197"))
                .situation(true)
                .name("Test Company")
                .inclusionUser("paulo")
                .build();

        Company savedCompany = companyRepository.save(company);

        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());
        assertThat(foundCompany).isPresent();

        companyRepository.delete(foundCompany.get());

        Optional<Company> deletedCompany = companyRepository.findById(savedCompany.getId());
        assertThat(deletedCompany).isNotPresent();
    }
}