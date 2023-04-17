package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    public ProfileDTO create(ProfileDTO dto,Integer id) {
        // check - homework
        isValidProfile(dto);

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5 ?
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(GeneralStatus.ACTIVE);
        entity.setVisible(true);
        profileRepository.save(entity); // save profile

        dto.setPassword(null);
        dto.setId(id);
        return dto;
    }

    public void isValidProfile(ProfileDTO dto) {
        // throw ...
    }
}
