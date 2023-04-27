package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "region")
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "visible")
    private Boolean visible=Boolean.TRUE;
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
    ///id,key,name_uz, name_ru, name_en,visible,created_date


    /*  kun_uz_project

Table
      Profile, Region, Category,ArticleType

API list
      Authorization (1,)
      Profile (1,5)
      ArticleType(1,5)
      Region(1,5)
      Category(1,5)*/
}
