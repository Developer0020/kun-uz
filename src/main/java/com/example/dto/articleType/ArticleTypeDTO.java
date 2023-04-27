package com.example.dto.articleType;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleTypeDTO {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;

}
