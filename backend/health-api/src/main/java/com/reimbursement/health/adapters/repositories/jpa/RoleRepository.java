package com.reimbursement.health.adapters.repositories.jpa;

import com.reimbursement.health.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
