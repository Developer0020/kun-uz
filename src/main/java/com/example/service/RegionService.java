package com.example.service;

import com.example.dto.region.RegionDTO;
import com.example.dto.region.RegionRequestDTO;
import com.example.entity.RegionEntity;
import com.example.exception.ItemNotFoundException;
import com.example.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {
        RegionEntity entity = toEntity(dto);
        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }
    public String update(RegionDTO dto, Integer id) {
        RegionEntity entity = getById(id);
        if (!dto.getNameUz().isBlank() || dto.getNameUz() != null) {
            entity.setNameUz(dto.getNameUz());
        }
        if (!dto.getNameRu().isBlank() || dto.getNameRu() != null) {
            entity.setNameRu(dto.getNameRu());
        }
        if (!dto.getNameEn().isBlank() || dto.getNameEn() != null) {
            entity.setNameEn(dto.getNameEn());
        }
        dto.setId(id);
        regionRepository.save(entity);
        return "Successful updated !!!";
    }
    public RegionEntity getById(Integer id) {
        RegionEntity entity = regionRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new ItemNotFoundException("item not found");
        }
        return entity;
    }
    public boolean delete(Integer id) {
        RegionEntity entity = getById(id);
        entity.setVisible(false);
        return true;
    }
    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
        List<RegionDTO> resultList = new LinkedList<>();
        iterable.forEach(entity -> resultList.add(toDTO(entity)));
        return resultList;
    }
    public List<RegionRequestDTO> getByLang(String lang) {
        List<RegionRequestDTO> response = new LinkedList<>();
        getAll().forEach(regionDTO -> {
            RegionRequestDTO dto = new RegionRequestDTO();
            switch (lang) {
                case "uz" -> {
                    dto.setName(regionDTO.getNameUz());
                    dto.setId(regionDTO.getId());
                    response.add(dto);
                }
                case "ru" -> {
                    dto.setName(regionDTO.getNameRu());
                    dto.setId(regionDTO.getId());
                    response.add(dto);
                }
                case "en" -> {
                    dto.setName(regionDTO.getNameEn());
                    dto.setId(regionDTO.getId());
                    response.add(dto);
                }
            }
        });
        return response;

    }
    public RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }
    public RegionEntity toEntity(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        return entity;
    }

}
