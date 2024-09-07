package com.reimbursement.health.domain.entities;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void builder() {
        var user = User.builder()
                .name("test")
                .login("test")
                .email("test@test.com")
                .inclusionUser("test")
                .build();

        Assert.notNull(user,"user not found");
    }
    
    @Test
    void update() {
        var user = new User();
        String name = "test";
        String login = "test";
        String updateUser = "test";
        String password = "test";
        String email = "test@test.com";
        
        user.update(name,login,email,Boolean.TRUE,Boolean.TRUE,updateUser);

        assertEquals(name, user.getName());
        assertEquals(updateUser, user.getUpdateUser());
        assertEquals(email, user.getEmail());
        assertTrue(user.getStatus());
        assertTrue(user.getIsPasswordTemporary());
    }

//    @Test
//    void passwordUpdate() {
//        var user = new User();
//        user.passwordUpdate("123456");
//
//        assertEquals("123456", user.getPassword());
//        assertFalse(user.getIsPasswordTemporary());
//    }
//    @Test
//    void passwordReset() {
//        var user = new User();
//        user.passwordReset("123456");
//
//        assertEquals("123456", user.getPassword());
//        assertTrue(user.getIsPasswordTemporary());
//    }

}