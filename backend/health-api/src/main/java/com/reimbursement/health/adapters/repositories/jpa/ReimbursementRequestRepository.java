package com.reimbursement.health.adapters.repositories.jpa;

import com.reimbursement.health.domain.entities.ReimbursementRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ReimbursementRequestRepository extends JpaRepository<ReimbursementRequest, UUID> {
}
