package com.example.repository;

import com.example.entity.TageEntity;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<TageEntity,Integer> {
}
