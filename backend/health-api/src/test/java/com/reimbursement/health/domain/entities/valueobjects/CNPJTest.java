package com.reimbursement.health.domain.entities.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CNPJTest {

    @Test
    void getNumero() {
        CNPJ cnpj = new CNPJ("18858694000168");
        assertEquals("18858694000168", cnpj.getNumero());
    }

    @Test
    void getNumeroFormatado() {
        CNPJ cnpj = new CNPJ("01599694000198");
        assertEquals("01.599.694/0001-98", cnpj.getNumeroFormatado());
    }
    @Test
    void cnpjInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CNPJ("01599694010158");
        }, "CNPJ inválido");
    }
}