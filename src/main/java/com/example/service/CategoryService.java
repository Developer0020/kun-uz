package com.example.service;

import com.example.dto.category.CategoryDTO;
import com.example.dto.category.CategoryRequestDTO;
import com.example.entity.CategoryEntity;
import com.example.exception.ItemNotFoundException;
import com.example.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity entity = toEntity(dto);
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }
    public String update(CategoryDTO dto, Integer id) {
        CategoryEntity entity = getById(id);
        if (!dto.getNameUz().isBlank() || dto.getNameUz() != null) {
            entity.setNameUz(dto.getNameUz());
        }
        if (!dto.getNameRu().isBlank() || dto.getNameRu() != null) {
            entity.setNameRu(dto.getNameRu());
        }
        if (!dto.getNameEn().isBlank() || dto.getNameEn() != null) {
            entity.setNameEn(dto.getNameEn());
        }
        categoryRepository.save(entity);
        return "Successful updated !!!";
    }
    private CategoryEntity getById(Integer id) {
        CategoryEntity entity = categoryRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new ItemNotFoundException("item not found");
        }
        return entity;
    }
    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }
    public CategoryEntity toEntity(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        return entity;
    }
    public boolean delete(Integer id) {
        CategoryEntity entity = getById(id);
        entity.setVisible(false);
        categoryRepository.save(entity);
        return true;
    }
    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> resultList = new LinkedList<>();
        iterable.forEach(entity -> resultList.add(toDTO(entity)));
        return resultList;
    }
    public List<CategoryRequestDTO> getByLang(String lang) {
        List<CategoryRequestDTO> response = new LinkedList<>();
        getAll().forEach(categoryDTO -> {
            CategoryRequestDTO dto = new CategoryRequestDTO();
            switch (lang) {
                case "uz" -> {
                    dto.setName(categoryDTO.getNameUz());
                    dto.setId(categoryDTO.getId());
                    response.add(dto);
                }
                case "ru" -> {
                    dto.setName(categoryDTO.getNameRu());
                    dto.setId(categoryDTO.getId());
                    response.add(dto);
                }
                case "en" -> {
                    dto.setName(categoryDTO.getNameEn());
                    dto.setId(categoryDTO.getId());
                    response.add(dto);
                }
            }
        });
        return response;

    }
}
