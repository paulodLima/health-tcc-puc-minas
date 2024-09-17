package com.reimbursement.health.domain.entities;

import com.reimbursement.health.domain.enuns.ReimbursementStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementRequestTest {

    @Test
    void update() {
        ReimbursementRequest request = new ReimbursementRequest();
        request.update("test",BigDecimal.TWO,new Company(),new User());

        assertEquals("test", request.getUpdateUser());
        assertEquals(BigDecimal.TWO, request.getAmount());
        assertEquals(ReimbursementStatus.PENDING, request.getStatus());
    }

    @Test
    void builder() {
        var reimbursementRequest = ReimbursementRequest.builder()
                .amount(BigDecimal.TEN)
                .company(new Company())
                .user(new User())
                .status(ReimbursementStatus.APPROVED)
                .inclusionUser("test")
                .build();

        assertNotNull(reimbursementRequest,"reimbursementRequest not found");
        assertEquals(BigDecimal.TEN,reimbursementRequest.getAmount());
    }
}