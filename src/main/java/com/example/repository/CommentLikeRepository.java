package com.example.repository;

import com.example.entity.CommentLikeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,Integer> {
}
