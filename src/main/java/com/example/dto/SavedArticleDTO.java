package com.example.dto;

import com.example.entity.ArticleEntity;
import com.example.entity.ProfileEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SavedArticleDTO {
    private Integer id;
    private ProfileEntity profile;
    private ArticleEntity article;
    private LocalDateTime createdDate;
}
