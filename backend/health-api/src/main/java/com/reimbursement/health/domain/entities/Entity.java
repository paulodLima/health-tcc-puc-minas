package com.reimbursement.health.domain.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class Entity {
    @Id
    private final UUID id;

    public Entity() {
        this.id = UUID.randomUUID();
    }

}
