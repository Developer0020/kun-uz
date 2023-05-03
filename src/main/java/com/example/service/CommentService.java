package com.example.service;

import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentRequestDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.exception.CommentNotFoundException;
import com.example.exception.MethodNotAllowedException;
import com.example.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.hibernate.collection.spi.PersistentBag;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final ArticleService articleService;
    private final ProfileService profileService;
    private final CommentRepository commentRepository;

    public boolean create(CommentRequestDTO dto, Integer profileId) {
        CommentEntity entity = new CommentEntity();
        entity.setArticle(articleService.get(dto.getArticleId()));
        entity.setProfile(profileService.getById(profileId));
        entity.setContent(dto.getContent());
        entity.setReplyId(dto.getReplyId());
        commentRepository.save(entity);
        return true;
    }

    public CommentEntity toEntity(CommentRequestDTO dto) {

        return null;
    }

    public CommentDTO toDTO(CommentEntity entity) {
        return null;
    }

    public CommentEntity get(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new CommentNotFoundException("Comment not found !!!");
        }
        return optional.get();
    }

    public boolean update(Integer commentId, CommentRequestDTO dto, Integer profileId) {
        CommentEntity entity = get(commentId);
        if (!entity.getProfile().getId().equals(profileId)) {
            throw new MethodNotAllowedException("method not allowed ???");
        }
        entity.setContent(dto.getContent());
        entity.setArticle(articleService.get(dto.getArticleId()));
        entity.setUpdateDate(LocalDateTime.now());
        commentRepository.save(entity);
        return true;
    }

    public List<CommentDTO> getByArticleCommentList(String articleId) {
        ArticleEntity response = articleService.get(articleId);
        List<CommentDTO> responseDTOList = new LinkedList<>();
        commentRepository.getByArticleIdCommentList(response).forEach(entity -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setUpdateDate(entity.getUpdateDate());
            dto.setProfileId(entity.getProfile().getId());
            dto.setPName(entity.getProfile().getName());
            dto.setPSurname(entity.getProfile().getSurname());
            responseDTOList.add(dto);
        });
        return responseDTOList;
    }

    public Page<CommentDTO> list(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CommentEntity> response = commentRepository.findAll(pageable);
        long totalCount = response.getTotalElements();
        List<CommentEntity> entityList = response.getContent();
        List<CommentDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setUpdateDate(entity.getUpdateDate());
            dto.setProfileId(entity.getProfile().getId());
            dto.setPName(entity.getProfile().getName());
            dto.setPSurname(entity.getProfile().getSurname());
            dto.setContent(entity.getContent());
            dto.setArticleId(entity.getArticle().getId());
            dto.setReplyId(entity.getReplyId());
            dtoList.add(dto);
        });
        return new PageImpl<CommentDTO>(dtoList,pageable,totalCount);
        //        (id,created_date,update_date,profile(id,name,surname),content,article(id,title),reply_id,)
    }
}
