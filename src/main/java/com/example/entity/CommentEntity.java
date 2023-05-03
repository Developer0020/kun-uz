package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "profile_id")
    @ManyToOne
    private ProfileEntity profile;
    @Column(name = "content")
    private String content;
    @Column(name = "article_id")
    @ManyToOne
    private ArticleEntity article;
    @Column(name = "reply_id")
    private Integer replyId;
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;


}
/*id,created_date,update_date,profile_id,content,article_id,reply_id,visible*/