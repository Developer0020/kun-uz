package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "saved-Article")
@AllArgsConstructor
@NoArgsConstructor
public class SavedArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @Column(name = "profile_id")
    private ProfileEntity profile;
    @ManyToOne
    @Column(name = "article_id")
    private ArticleEntity article;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
