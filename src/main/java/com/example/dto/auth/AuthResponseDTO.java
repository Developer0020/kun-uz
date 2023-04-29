package com.example.dto.auth;

import com.example.enums.ProfileRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "surname cannot be null")
    private String surname;
    @NotNull(message = "role cannot be null")
    private ProfileRole role;
    @NotNull(message = "null")
    private String jwt;
}
