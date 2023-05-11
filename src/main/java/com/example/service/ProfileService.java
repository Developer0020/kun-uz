package com.example.service;

import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileFilterRequestDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.exception.ItemNotFoundException;
import com.example.exception.MethodNotAllowedException;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@AllArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileCustomRepository profileCustomRepository;

    public ProfileDTO create(ProfileDTO dto) {
        // check - homework
        isValidProfile(dto);
        ProfileEntity entity = toEntity(dto);
        profileRepository.save(entity); // save profile
        dto.setId(entity.getId());
        dto.setPassword(null);
        return dto;
    }

    public ProfileDTO update(ProfileDTO dto, Integer id) {
        dto.setId(id);
        profileRepository.save(checkDTO(dto));
        return dto;
    }

    public ProfileDTO get(Integer id) {
        ProfileEntity entity = getById(id);
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        return dto;
    }
    public ProfileEntity getById(Integer id) {
        ProfileEntity entity = profileRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new ItemNotFoundException("item not found");
        }
        return entity;
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
    public List<ProfileDTO> getAll() {
        Iterable<ProfileEntity> iterable = profileRepository.findAll();
        List<ProfileDTO> dtoList = new LinkedList<>();
        iterable.forEach(profileEntity -> {
            dtoList.add(toDTO(profileEntity));
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
    public ProfileDTO toDTO(ProfileEntity entity) {
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
    public ProfileEntity toEntity(ProfileDTO dto) {
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

//    public List<ProfileDTO> filter(ProfileFilterRequestDTO filterRequestDTO) {
////        List<ProfileEntity> entityList = profileCustomRepository.filter(filterRequestDTO);
//        List<ProfileDTO> dtoList = new LinkedList<>();
//        for (ProfileEntity entity : entityList) {
//            dtoList.add(toDTO(entity));
//        }
//        return dtoList;
//    }

}
