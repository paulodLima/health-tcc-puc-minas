package com.reimbursement.health.adapters.repositories.jpa;

import com.reimbursement.health.domain.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findByMenuPai(Menu menuPai);

    @Query("SELECT m FROM Menu m JOIN m.roles r WHERE r.nome = :roleName")
    List<Menu> findByRoleName(@Param("roleName") String roleName);
}
