package com.example.entity;

import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "content")
    private String content;
    @Column(name = "shared_count")
    private Integer sharedCount;
    @JoinColumn(name = "image_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ImageEntity image;
    @JoinColumn(name = "region_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;
    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;
    @JoinColumn(name = "moderator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity moderator;
    @JoinColumn(name = "publisher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity publisher;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ArticleStatus status = ArticleStatus.NOTPUBLISHED;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    @Column(name = "visible")
    private Boolean visible;
    @Column(name = "view_count")
    private Integer viewCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ArticleTypeEntity articleType;
}
/*id(uuid),title,description,content,shared_count,image_id,
    region_id,category_id,moderator_id,publisher_id,status(Published,NotPublished)
    created_date,published_date,visible,view_count*/