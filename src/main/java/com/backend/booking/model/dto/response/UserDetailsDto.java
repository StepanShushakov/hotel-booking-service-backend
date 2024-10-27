package com.backend.booking.model.dto.response;

import com.backend.booking.model.enums.UserRole;
import lombok.Data;

@Data
public class UserDetailsDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private UserRole role;

}
