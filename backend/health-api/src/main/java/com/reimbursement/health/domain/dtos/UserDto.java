package com.reimbursement.health.domain.dtos;

import com.reimbursement.health.domain.entities.User;
import jakarta.persistence.Column;
import lombok.*;
import org.keycloak.representations.idm.CredentialRepresentation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String name;
    private String login;
    private String email;
    private Boolean status;
    private LocalDateTime inclusionDate;
    private LocalDateTime updateDate;
    private String inclusionUser;
    private String updateUser;

    public static UserDto toDto(User user) {
        if (user == null) return null;

        var dto  = new UserDto();
        dto.setId(user.getId().toString());
        dto.setName(user.getName());
        dto.setLogin(user.getLogin());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        dto.setInclusionDate(user.getInclusionDate());

        return dto;
    }
}
