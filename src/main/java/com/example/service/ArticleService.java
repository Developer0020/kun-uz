package com.example.service;

import com.example.dto.article.ArticleFullInfoDTO;
import com.example.dto.article.ArticleRequestDTO;
import com.example.dto.article.ArticleShortInfoDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.example.exception.ArticleNotFoundException;
import com.example.repository.ArticleRepository;
import com.example.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {
//    @Value("${server.host}")
//    private  String serverHost;
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final AttachService attachService;
    private final ArticleTypeService articleTypeService;
    private final RegionService regionService;
    public ArticleRequestDTO create(ArticleRequestDTO dto, Integer id) {
        ArticleEntity entity = toEntity(dto);
        articleRepository.save(entity);
        return dto;
    }
    public ArticleRequestDTO update(ArticleRequestDTO dto, Integer moderId ,String id) {
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
    public boolean delete(String id) {
        ArticleEntity entity = articleRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new RuntimeException("this article is null");
        }
        entity.setVisible(false);
        articleRepository.save(entity);
        return true;
    }
    public Boolean changeStatus11111111111111(ArticleStatus status, String id, Integer prtId) {
        ArticleEntity entity = get(id);
        if (status.equals(ArticleStatus.PUBLISHED)) {
            entity.setPublishedDate(LocalDateTime.now());
            entity.setPublisherId(prtId);
        }
        entity.setStatus(status);
        articleRepository.save(entity);
        // articleRepository.changeStatus(status, id);
        return true;
    }
    public String changeStatus(ArticleStatus status, String id) {
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
        List<ArticleEntity> entityList = articleRepository.articleShortInfo(typeId, ArticleStatus.PUBLISHED.name(), count);
        List<ArticleShortInfoDTO> responseDTOList = new LinkedList<>();
        entityList.forEach(entity -> {
            responseDTOList.add(new ArticleShortInfoDTO
                    (entity.getId(), entity.getTitle(), entity.getDescription(), entity.getPublishedDate(), entity.getAttach()));
        });
        return responseDTOList;
    }
    public List<ArticleShortInfoDTO> getLastGivenList(List<Integer> countList) {
        // TODO: 4/29/2023
        return null;
    }
    public ArticleEntity get(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ArticleNotFoundException("article not found");
        }
        return optional.get();
    }
    public ArticleFullInfoDTO articleFullInfo(String id, String lang) {
        ArticleEntity entity = get(id);
        ArticleFullInfoDTO dto = new ArticleFullInfoDTO();
        dto.setId(entity.getId());
        dto.setRegion(entity.getRegion());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setSharedCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setTitle(entity.getTitle());
        return dto;
    }
    public ArticleShortInfoDTO toArticleShortInfo(ArticleEntity entity) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublishedDate());
//        dto.setAttach(attachService.getAttachLink(entity.getAttachId()));
        return dto;
    }
    public List<ArticleShortInfoDTO> getLast5ByTypeId(Integer typeId) {
        List<ArticleEntity> entityList = articleRepository.findTop5ByArticleTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(typeId,
                ArticleStatus.PUBLISHED, true);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }

    public List<ArticleShortInfoDTO> get4ArticleByTypes(Integer typeId, String articleId) {
        ArticleTypeEntity type = articleTypeService.getById(typeId);
        List<ArticleEntity> entityList = articleRepository.findByTypeIdAndIdNot(type, articleId);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toShortInfo(entity));
        }
        return dtoList;
    }
    public ArticleShortInfoDTO toShortInfo(ArticleEntity entity) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setAttach(attachService.getAttachLink(entity.getAttachId()));
        dto.setId(entity.getId());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        return dto;
    }


    public List<ArticleShortInfoDTO> articleShortInfo(String type, String regionName) {
        ArticleTypeEntity typeEntity = articleTypeService.get(type);
        RegionEntity region = regionService.get(regionName);
        List<ArticleEntity> entityList = articleRepository.findByTypeAndRegion(typeEntity, region);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toShortInfo(entity));
        }
        return dtoList;
    }

}
