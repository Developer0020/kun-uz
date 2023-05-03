package com.example.repository;

import com.example.entity.SavedArticleEntity;
import org.springframework.data.repository.CrudRepository;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity,Integer> {
}
