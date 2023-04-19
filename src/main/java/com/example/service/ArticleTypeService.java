package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.ArticleTypeRequestDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.repository.ArticleTypeRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        if (getById(dto.getId()) != null) {
            throw new RuntimeException(" not null ");
        }
        articleTypeRepository.save(DTOToEntity(dto));
        return dto;
    }

    public ArticleTypeDTO update(ArticleTypeDTO dto, Integer id) {
        if (getById(id) == null) {
            throw new RuntimeException("this articleType is null");
        }
        articleTypeRepository.save(checkDTO(dto));
        dto.setId(id);
        return dto;
    }

    private ArticleTypeEntity getById(Integer id) {
        return articleTypeRepository.findById(id).orElse(null);
    }


    public ArticleTypeDTO entityToDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }

    public ArticleTypeEntity DTOToEntity(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        return entity;
    }

    public ArticleTypeEntity checkDTO(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = getById(dto.getId());
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
        ArticleTypeEntity entity = getById(id);
        if (entity == null) {
            throw new RuntimeException("entity is null");
        }
        entity.setVisible(false);
        return true;
    }

    public List<ArticleTypeDTO> getAll() {
        Iterable<ArticleTypeEntity> iterable = articleTypeRepository.findAll();
        List<ArticleTypeDTO> resultList = new LinkedList<>();
        iterable.forEach(entity -> {
            resultList.add(entityToDTO(entity));
        });
        return resultList;
    }

    public List<ArticleTypeRequestDTO> getByLang(String lang) {
        List<ArticleTypeRequestDTO> response = new LinkedList<>();
        getAll().forEach(articleTypeDTO -> {
            ArticleTypeRequestDTO dto = new ArticleTypeRequestDTO();
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
