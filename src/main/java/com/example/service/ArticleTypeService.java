package com.example.service;

import com.example.dto.articleType.ArticleTypeDTO;
import com.example.dto.articleType.ArticleTypeRequestDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.exception.ArticleTypeNotFoundException;
import com.example.exception.ItemNotFoundException;
import com.example.repository.ArticleTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ArticleTypeService {
    private final ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = toEntity(dto);
        articleTypeRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }
    public String update(ArticleTypeDTO dto, Integer id) {
        ArticleTypeEntity entity=getById(id);
        if (!dto.getNameUz().isBlank() || dto.getNameUz() != null) {
            entity.setNameUz(dto.getNameUz());
        }
        if (!dto.getNameRu().isBlank() || dto.getNameRu() != null) {
            entity.setNameRu(dto.getNameRu());
        }
        if (!dto.getNameEn().isBlank() || dto.getNameEn() != null) {
            entity.setNameEn(dto.getNameEn());
        }
        articleTypeRepository.save(entity);
        return "Successful updated !!!";
    }
    public ArticleTypeEntity getById(Integer id) {
        ArticleTypeEntity entity=articleTypeRepository.findById(id).orElse(null);
        if (entity==null){
            throw new ItemNotFoundException("item not found");
        }
        return entity;
    }
    public ArticleTypeDTO toDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }
    public ArticleTypeEntity toEntity(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        return entity;
    }
    public boolean delete(Integer id) {
        ArticleTypeEntity entity = getById(id);
        entity.setVisible(false);
        articleTypeRepository.save(entity);
        return true;
    }
    public List<ArticleTypeDTO> getAll() {
        Iterable<ArticleTypeEntity> iterable = articleTypeRepository.findAll();
        List<ArticleTypeDTO> resultList = new LinkedList<>();
        iterable.forEach(entity -> resultList.add(toDTO(entity)));
        return resultList;
    }
    public List<ArticleTypeRequestDTO> getByLang(String lang) {
        List<ArticleTypeRequestDTO> response = new LinkedList<>();
        getAll().forEach(articleTypeDTO -> {
            ArticleTypeRequestDTO dto = new ArticleTypeRequestDTO();
            switch (lang) {
                case "uz" -> {
                    dto.setName(articleTypeDTO.getNameUz());
                    dto.setId(articleTypeDTO.getId());
                    response.add(dto);
                }
                case "ru" -> {
                    dto.setName(articleTypeDTO.getNameRu());
                    dto.setId(articleTypeDTO.getId());
                    response.add(dto);
                }
                case "en" -> {
                    dto.setName(articleTypeDTO.getNameEn());
                    dto.setId(articleTypeDTO.getId());
                    response.add(dto);
                }
            }
        });
        return response;

    }
    public ArticleTypeEntity get(String name) {
        ArticleTypeEntity entity = articleTypeRepository.findByName(name);
        if (entity == null) {
            throw new ArticleTypeNotFoundException("not fount type");
        }
        return entity;
    }
}
