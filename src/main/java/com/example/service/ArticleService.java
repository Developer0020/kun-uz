package com.example.service;

import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleRequestDTO;
import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.repository.ArticleRepository;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ArticleRequestDTO create(ArticleRequestDTO dto, Integer id) {
        ArticleEntity entity = DTOToEntity(dto);
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
        if (dto.getSharedCount() != null) {
            entity.setSharedCount(dto.getSharedCount());
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

    public String changeStatus(ArticleStatus status,Integer id) {
        ArticleEntity entity =articleRepository.findById(id).orElse(null);
        if (entity==null){
            throw new RuntimeException("entity is null");
        }
        entity.setStatus(status);
        articleRepository.save(entity);
        return "changed !!! ";
    }

    public ArticleEntity DTOToEntity(ArticleRequestDTO dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setContent(dto.getContent());
        entity.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        entity.setDescription(dto.getDescription());
        entity.setSharedCount(dto.getSharedCount());
        return entity;
    }
}
