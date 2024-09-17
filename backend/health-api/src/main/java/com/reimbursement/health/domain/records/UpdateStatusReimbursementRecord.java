package com.reimbursement.health.domain.records;

import lombok.Builder;

@Builder
public record UpdateStatusReimbursementRecord(Integer status,String observation) {
}
