package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.example.enums.ArticleStatus.PUBLISHED;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Integer>,
        PagingAndSortingRepository<ArticleEntity, Integer> {
    @Query(value = "select * from article where type_id = :type_id and status = :status order by created_date desc limit :count",nativeQuery = true)
    List<ArticleEntity> articleShortInfo(@Param("type_id")Integer typeId, @Param("status") ArticleStatus status,@Param("count")Integer count);

}
/*Berilgan types bo'yicha oxirgi 5ta pubished bo'lgan article ni return qiladi*/