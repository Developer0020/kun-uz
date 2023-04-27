package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.exception.MethodNotAllowedException;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto) {
        // check - homework
        isValidProfile(dto);
        ProfileEntity entity = DTOToEntity(dto);
        profileRepository.save(entity); // save profile
        dto.setId(entity.getId());
        dto.setPassword(null);
        return dto;
    }

    public ProfileDTO update(ProfileDTO dto,Integer id) {
        dto.setId(id);
        profileRepository.save(checkDTO(dto));
        return dto;
    }

    public ProfileDTO updateDetail(ProfileDTO dto, Integer id) {
        if (getById(dto.getId()) == null) {
            throw new RuntimeException("this user is null");
        }
        if (id != dto.getId()) {
            throw new MethodNotAllowedException("Method not allowed !!!");
        }
        dto.setId(id);
        profileRepository.save(checkDTO(dto));
        return dto;
    }

    public ProfileEntity getById(Integer id) {
        return profileRepository.findById(id).orElse(null);
    }

    public List<ProfileDTO> getAll() {
        Iterable<ProfileEntity> iterable = profileRepository.findAll();
        List<ProfileDTO> dtoList = new LinkedList<>();
        iterable.forEach(profileEntity -> {
            dtoList.add(entityToDTO(profileEntity));
        });
        return dtoList;
    }

    public boolean delete(Integer id) {
        ProfileEntity entity = getById(id);
        if (entity == null) {
            throw new RuntimeException("this user is null");
        }
        entity.setVisible(false);
        profileRepository.save(entity);
        return true;
    }

    public ProfileDTO entityToDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setPhone(entity.getPhone());
        dto.setName(entity.getName());
        dto.setPassword(entity.getPassword());
        dto.setSurname(entity.getSurname());
        return dto;
    }

    public ProfileEntity DTOToEntity(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5 ?
        entity.setStatus(GeneralStatus.ACTIVE);
        return entity;
    }

    public ProfileEntity checkDTO(ProfileDTO dto) {
        ProfileEntity entity = getById(dto.getId());
        if (!dto.getEmail().isBlank() || dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (!dto.getPhone().isBlank() || dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }
        if (!dto.getName().isBlank() || dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (!dto.getSurname().isBlank() || dto.getSurname() != null) {
            entity.setSurname(dto.getSurname());
        }
        if (!dto.getPassword().isBlank() || dto.getPassword() != null) {
            entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        }
        return entity;
    }

    public void isValidProfile(ProfileDTO dto) {
        // throw ...
    }


}
