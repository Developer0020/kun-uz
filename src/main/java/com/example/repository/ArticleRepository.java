package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.example.mapper.ArticleShortInfoMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.example.enums.ArticleStatus.PUBLISHED;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String>,
        PagingAndSortingRepository<ArticleEntity, String> {
    @Query(value = "select * from article where type_id = :type_id and status = :status order by created_date desc limit :count",nativeQuery = true)
    List<ArticleEntity> articleShortInfo(@Param("type_id")Integer typeId, @Param("status") String status,@Param("count")Integer count);

    List<ArticleEntity> findTop5ByArticleTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(Integer articleTypeId, ArticleStatus published, boolean b);
//    @Query("SELECT new ArticleEntity(id,title,description,attachId,publishedDate) From ArticleEntity where status =:status and visible = true and articleTypeId=:typeId order by createdDate desc limit 5")
//    List<ArticleEntity> find5ByTypeId(@Param("typeId") Integer typeId, @Param("status") String status);
    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:typeId and status =:status order by created_date desc Limit :limit",
            nativeQuery = true)
    List<ArticleShortInfoMapper> getTopN(@Param("typeId") Integer typeId, @Param("status") String status, @Param("limit") Integer limit);

    List<ArticleEntity> findByTypeIdAndIdNot(ArticleTypeEntity type, String articleId);

    List<ArticleEntity> findByTypeAndRegion(ArticleTypeEntity typeEntity, RegionEntity region);
}
/*Berilgan types bo'yicha oxirgi 5ta pubished bo'lgan article ni return qiladi*/