package com.example.dto.article;

import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ArticleRequestDTO {
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer imageId;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private LocalDateTime publishedDate;
    private Integer viewCount;
}
