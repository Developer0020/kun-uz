package com.example.service;

import com.example.dto.SavedArticleDTO;
import com.example.entity.SavedArticleEntity;
import com.example.exception.ArticleNotFoundException;
import com.example.repository.SavedArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SavedArticleService {
    private final SavedArticleRepository savedArticleRepository;
    private final ArticleService articleService;
    private final ProfileService profileService;

    public boolean create(String articleId, Integer profileId) {
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticle(articleService.get(articleId));
        entity.setProfile(profileService.getById(profileId));
        savedArticleRepository.save(entity);
        return true;
    }
    public boolean delete(Integer id) {
        SavedArticleEntity entity = get(id);
        savedArticleRepository.delete(entity);
        return true;
    }
    public SavedArticleEntity get(Integer id) {
        Optional<SavedArticleEntity> optional = savedArticleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ArticleNotFoundException("article not found  ? ? ?");
        }
        return optional.get();
    }
    public List<SavedArticleDTO> list() {
        List<SavedArticleDTO> response = new LinkedList<>();
        savedArticleRepository.findAll().forEach(entity -> {
            SavedArticleDTO dto = new SavedArticleDTO();
            dto.setArticle(entity.getArticle());
            dto.setProfile(entity.getProfile());
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            response.add(dto);
        });
        return response;
    }
}
