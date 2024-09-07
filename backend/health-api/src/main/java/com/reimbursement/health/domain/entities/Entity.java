package com.reimbursement.health.domain.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class Entity {
    @Id
    private UUID id;

    public Entity() {
    }

    @PostLoad
    private void postLoad() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
