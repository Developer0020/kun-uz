package com.example.dto.profile;

import com.example.enums.ProfileRole;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Name;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterRequestDTO {
    @NotNull(message = "name is null")
    private String name;
    @NotNull(message = "surname is null")
    private String surname;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
}
