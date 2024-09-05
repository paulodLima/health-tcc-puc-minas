package com.reimbursement.health.domain.entities.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Embeddable
public class CNPJ {
    private String cnpj;

    protected CNPJ(){};

    public CNPJ(String cnpj) {
        if (cnpj == null || !isValid(cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
        this.cnpj = cnpj.replaceAll("[^\\d]", "");
    }

    public String getNumero(){
        return this.cnpj;
    }
    public String getNumeroFormatado(){
        return formatCNPJ(this.cnpj);
    }
    private static boolean isValid(String cnpj) {
        String cleanCNPJ = cnpj.replaceAll("[^\\d]", "");
        if (cleanCNPJ.length() != 14) {
            return false;
        }

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int firstDigit = calculateDigit(cleanCNPJ, weights1);
        int secondDigit = calculateDigit(cleanCNPJ + firstDigit, weights2);

        return cleanCNPJ.equals(cleanCNPJ.substring(0, 12) + firstDigit + secondDigit);
    }

    private static int calculateDigit(String cnpj, int[] weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += (cnpj.charAt(i) - '0') * weights[i];
        }
        int remainder = (sum % 11);
        return (remainder < 2) ? 0 : 11 - remainder;
    }

    private static String formatCNPJ(String cnpj) {
        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }
}
