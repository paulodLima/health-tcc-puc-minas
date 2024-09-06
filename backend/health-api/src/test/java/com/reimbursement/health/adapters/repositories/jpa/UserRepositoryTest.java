package com.reimbursement.health.adapters.repositories.jpa;

import com.reimbursement.health.domain.entities.Company;
import com.reimbursement.health.domain.entities.User;
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
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindUser(){
        var user = User.builder()
                .username("test")
                .email("test@test.com")
                .password("test")
                .inclusionUser("test")
                .build();

        var savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("test");
        assertThat(foundUser.get().getEmail()).isEqualTo("test@test.com");
        assertTrue(foundUser.get().getStatus());
    }

    @Test
    public void testSaveAndUpdateUser(){
        var user = User.builder()
                .username("test")
                .email("test@test.com")
                .password("test")
                .inclusionUser("test")
                .build();

        var savedCompany = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedCompany.getId());

        assertThat(foundUser).isPresent();

        String newName = "New Name";
        String email = "p@mail.com";
        String updateUser = "testUser";

        foundUser.get().update(newName,savedCompany.getPassword(),email,Boolean.FALSE,Boolean.FALSE,updateUser);

        userRepository.save(foundUser.get());

        Optional<User> updatedUser = userRepository.findById(foundUser.get().getId());

        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getUsername()).isEqualTo("New Name");
        assertThat(updatedUser.get().getEmail()).isEqualTo("p@mail.com");
        assertFalse(updatedUser.get().getStatus());
    }

    @Test
    public void testSaveAndDeleteUser(){
        var user = User.builder()
                .username("test")
                .email("test@test.com")
                .password("test")
                .inclusionUser("test")
                .build();

        var savedUser = userRepository.save(user);

        Optional<User> foundCompany = userRepository.findById(savedUser.getId());
        assertThat(foundCompany).isPresent();

        userRepository.delete(foundCompany.get());

        Optional<User> deletedCompany = userRepository.findById(savedUser.getId());
        assertThat(deletedCompany).isNotPresent();
    }
}