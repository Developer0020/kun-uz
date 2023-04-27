package com.example.dto.article;

import com.example.entity.CategoryEntity;
import com.example.entity.ImageEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDTO {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer imageId;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status ;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewCount;
}
