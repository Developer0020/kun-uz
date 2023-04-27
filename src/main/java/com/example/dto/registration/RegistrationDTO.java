package com.example.dto.registration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
}
