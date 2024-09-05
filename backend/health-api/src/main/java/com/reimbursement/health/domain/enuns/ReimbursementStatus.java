package com.reimbursement.health.domain.enuns;

import lombok.Getter;

@Getter
public enum ReimbursementStatus { PENDING(1), APPROVED(2), REJECTED(3);
    private final int value;

    ReimbursementStatus(int value) {
        this.value = value;
    }

    public static ReimbursementStatus fromValue(int value) {
        for (ReimbursementStatus status : values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

