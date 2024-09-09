package com.reimbursement.health.adapters.repositories.jpa;

import com.reimbursement.health.domain.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findByMenuPai(Menu menuPai);
}
