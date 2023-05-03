package com.example.service;

import com.example.dto.tag.TagDTO;
import com.example.dto.tag.TagRequestDTO;
import com.example.entity.TageEntity;
import com.example.exception.TagNotFoundException;
import com.example.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    public boolean create(TagDTO dto) {
        TageEntity entity = new TageEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);
        return true;
    }
    public boolean update(Integer id, TagRequestDTO dto) {
        TageEntity entity = get(id);
        entity.setName(dto.getName());
        tagRepository.save(entity);
        return true;
    }
    public TageEntity get(Integer id) {
        Optional<TageEntity> optional = tagRepository.findById(id);
        if (optional.isEmpty()) {
            throw new TagNotFoundException("tag not found");
        }
        return optional.get();
    }
    public boolean delete(Integer id) {
        TageEntity entity = get(id);
        tagRepository.delete(entity);
        return true;
    }
    public List<TagDTO> getAll() {
        List<TagDTO> response = new LinkedList<>();
        tagRepository.findAll().forEach(entity -> {
            TagDTO dto = new TagDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setCreatedDate(entity.getCreatedDate());
            response.add(dto);
        });
        return response;
    }
}
