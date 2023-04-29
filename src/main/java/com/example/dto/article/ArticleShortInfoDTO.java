package com.example.dto.article;

import com.example.entity.AttachEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
public class ArticleShortInfoDTO {
    private String id;
    private String title;
    private String description;
    private LocalDate publishedDate;
    private AttachEntity attach;
}
//         id(uuid),title,description,image(id,url),published_date