package com.reimbursement.health.domain.entities;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@jakarta.persistence.Entity
@Table(name = "TB_ROLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends Entity{
    private String nome;

    @ManyToMany(mappedBy = "roles")
    private List<Menu> menus;
}
