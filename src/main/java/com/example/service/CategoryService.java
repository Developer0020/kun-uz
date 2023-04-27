package com.example.service;

import com.example.dto.category.CategoryDTO;
import com.example.dto.category.CategoryRequestDTO;
import com.example.entity.CategoryEntity;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity entity = DTOToEntity(dto);
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public CategoryDTO update(CategoryDTO dto, Integer id) {
        if (getById(id) == null) {
            throw new RuntimeException("this category is null");
        }
        dto.setId(id);
        categoryRepository.save(checkDTO(dto));
        return dto;
    }

    private CategoryEntity getById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }


    public CategoryDTO entityToDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }

    public CategoryEntity DTOToEntity(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        return entity;
    }

    public CategoryEntity checkDTO(CategoryDTO dto) {
        CategoryEntity entity = getById(dto.getId());
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
        CategoryEntity entity = getById(id);
        if (entity == null) {
            throw new RuntimeException("entity is null");
        }
        entity.setVisible(false);
        categoryRepository.save(entity);
        return true;
    }

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> resultList = new LinkedList<>();
        iterable.forEach(entity -> {
            resultList.add(entityToDTO(entity));
        });
        return resultList;
    }

    public List<CategoryRequestDTO> getByLang(String lang) {
        List<CategoryRequestDTO> response = new LinkedList<>();
        getAll().forEach(categoryDTO -> {
            CategoryRequestDTO dto = new CategoryRequestDTO();
            if (lang.equals("uz")) {
                dto.setName(categoryDTO.getNameUz());
                dto.setId(categoryDTO.getId());
                response.add(dto);
            } else if (lang.equals("ru")) {
                dto.setName(categoryDTO.getNameRu());
                dto.setId(categoryDTO.getId());
                response.add(dto);
            } else if (lang.equals("en")) {
                dto.setName(categoryDTO.getNameEn());
                dto.setId(categoryDTO.getId());
                response.add(dto);
            }
        });
        return response;

    }
}
