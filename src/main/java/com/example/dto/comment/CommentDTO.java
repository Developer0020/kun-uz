package com.example.dto.comment;

import com.example.dto.profile.ProfileDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.ProfileEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    ///p this is - profile
    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private Integer profileId;
    private String pName;
    private String pSurname;
    private String content;
    private String articleId;
    private Integer replyId;
    private Boolean visible;
}
