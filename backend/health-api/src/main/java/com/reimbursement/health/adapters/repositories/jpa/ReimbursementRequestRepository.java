package com.reimbursement.health.adapters.repositories.jpa;

import com.reimbursement.health.domain.entities.ReimbursementRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReimbursementRequestRepository extends JpaRepository<ReimbursementRequest, UUID> {
    @Query("SELECT r FROM ReimbursementRequest r JOIN r.user u WHERE u.id = :id")
    List<ReimbursementRequest> findByIdUser(UUID id);
}
