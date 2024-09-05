package com.reimbursement.health.adapters.repositories.jpa;

import com.reimbursement.health.domain.entities.MedicalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicalRequestRepository extends JpaRepository<MedicalRequest, UUID> {
}
