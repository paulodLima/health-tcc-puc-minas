package com.reimbursement.health.domain.dtos;

import com.reimbursement.health.domain.entities.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String email;
    private Boolean status;
    private LocalDateTime inclusionDate;
    private String inclusionUser;
    private LocalDateTime updateDate;
    private String updateUser;

    public static UserDto toDto(User user) {
        if (user == null) return null;

        var dto  = new UserDto();
        dto.setId(user.getId().toString());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        dto.setInclusionDate(user.getInclusionDate());
        dto.setInclusionUser(user.getInclusionUser());
        dto.setUpdateDate(user.getUpdateDate());
        dto.setUpdateUser(user.getUpdateUser());

        return dto;
    }
}
