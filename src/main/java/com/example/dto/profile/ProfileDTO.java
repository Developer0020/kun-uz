package com.example.dto.profile;

import com.example.enums.ProfileRole;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileRole role;

}
