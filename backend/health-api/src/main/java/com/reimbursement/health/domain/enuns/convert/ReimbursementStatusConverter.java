package com.reimbursement.health.domain.enuns.convert;

import com.reimbursement.health.domain.enuns.ReimbursementStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReimbursementStatusConverter implements AttributeConverter<ReimbursementStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReimbursementStatus attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public ReimbursementStatus convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : ReimbursementStatus.fromValue(dbData);
    }
}