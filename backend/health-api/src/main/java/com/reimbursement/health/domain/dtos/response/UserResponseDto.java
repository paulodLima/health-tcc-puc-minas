package com.reimbursement.health.domain.dtos.response;

import com.reimbursement.health.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String name;
    private String email;

    public static UserResponseDto toDto(User user) {
        if (user == null) return null;

        var dto  = new UserResponseDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
