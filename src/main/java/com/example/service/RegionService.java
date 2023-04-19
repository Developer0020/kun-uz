package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.ArticleTypeRequestDTO;
import com.example.dto.RegionDTO;
import com.example.dto.RegionRequestDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.RegionEntity;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {
        if (getById(dto.getId()) != null) {
            throw new RuntimeException(" not null ");
        }
        regionRepository.save(DTOToEntity(dto));
        return dto;
    }

    public RegionDTO update(RegionDTO dto, Integer id) {
        if (getById(id) == null) {
            throw new RuntimeException("this articleType is null");
        }
        regionRepository.save(checkDTO(dto));
        dto.setId(id);
        return dto;
    }

    private RegionEntity getById(Integer id) {
        return regionRepository.findById(id).orElse(null);
    }

    public RegionDTO entityToDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }

    public RegionEntity DTOToEntity(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        return entity;
    }

    public RegionEntity checkDTO(RegionDTO dto) {
        RegionEntity entity = getById(dto.getId());
        if (!dto.getNameUz().isBlank() || dto.getNameUz() != null) {
            entity.setNameUz(dto.getNameUz());
        }
        if (!dto.getNameRu().isBlank() || dto.getNameRu() != null) {
            entity.setNameRu(dto.getNameRu());
        }
        if (!dto.getNameEn().isBlank() || dto.getNameEn() != null) {
            entity.setNameEn(dto.getNameEn());
        }
        return entity;
    }

    public boolean delete(Integer id) {
        RegionEntity entity = getById(id);
        if (entity == null) {
            throw new RuntimeException("entity is null");
        }
        entity.setVisible(false);
        return true;
    }

    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
        List<RegionDTO> resultList = new LinkedList<>();
        iterable.forEach(entity -> {
            resultList.add(entityToDTO(entity));
        });
        return resultList;
    }

    public List<RegionRequestDTO> getByLang(String lang) {
        List<RegionRequestDTO> response = new LinkedList<>();
        getAll().forEach(articleTypeDTO -> {
            RegionRequestDTO dto = new RegionRequestDTO();
            if (lang.equals("uz")) {
                dto.setName(articleTypeDTO.getNameUz());
                dto.setId(articleTypeDTO.getId());
                response.add(dto);
            } else if (lang.equals("ru")) {
                dto.setName(articleTypeDTO.getNameRu());
                dto.setId(articleTypeDTO.getId());
                response.add(dto);
            } else if (lang.equals("en")) {
                dto.setName(articleTypeDTO.getNameEn());
                dto.setId(articleTypeDTO.getId());
                response.add(dto);
            }
        });
        return response;

    }
}
