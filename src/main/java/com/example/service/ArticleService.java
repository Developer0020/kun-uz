package com.example.service;

import com.example.dto.article.ArticleRequestDTO;
import com.example.dto.article.ArticleShortInfoDTO;
import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.repository.ArticleRepository;
import com.example.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    public ArticleRequestDTO create(ArticleRequestDTO dto, Integer id) {
        ArticleEntity entity = toEntity(dto);
        articleRepository.save(entity);
        return dto;
    }
    public ArticleRequestDTO update(ArticleRequestDTO dto, Integer id) {
        ArticleEntity entity = articleRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new RuntimeException("this article is null");
        }
        if (dto.getCategoryId() != null) {
            entity.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getContent() != null) {
            entity.setContent(dto.getContent());
        }
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        articleRepository.save(entity);
        return dto;
    }
    public boolean delete(Integer id) {
        ArticleEntity entity = articleRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new RuntimeException("this article is null");
        }
        entity.setVisible(false);
        articleRepository.save(entity);
        return true;
    }
    public String changeStatus(ArticleStatus status, Integer id) {
        ArticleEntity entity = articleRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new RuntimeException("entity is null");
        }
        entity.setStatus(status);
        articleRepository.save(entity);
        return "changed !!! ";
    }
    public ArticleEntity toEntity(ArticleRequestDTO dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setContent(dto.getContent());
        entity.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        entity.setDescription(dto.getDescription());
        return entity;
    }
    public List<ArticleShortInfoDTO> getLastByCount(Integer typeId, Integer count) {
        List<ArticleEntity> entityList = articleRepository.articleShortInfo(typeId, ArticleStatus.PUBLISHED, count);
        List<ArticleShortInfoDTO> responseDTOList = new LinkedList<>();
        entityList.forEach(entity -> {
            responseDTOList.add(new ArticleShortInfoDTO
                    (entity.getId(), entity.getTitle(), entity.getDescription(), entity.getPublishedDate(), entity.getAttach()));
        });
        return responseDTOList;
    }

    public List<ArticleShortInfoDTO> getLastGivenList(List<Integer> countList) {

    }
}
