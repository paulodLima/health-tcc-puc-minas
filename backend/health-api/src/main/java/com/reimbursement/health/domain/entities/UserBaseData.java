package com.reimbursement.health.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBaseData {
    private Boolean status;
    private String name;
}
