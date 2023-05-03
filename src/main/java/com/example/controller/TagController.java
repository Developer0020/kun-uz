package com.example.controller;

import com.example.dto.tag.TagDTO;
import com.example.dto.tag.TagRequestDTO;
import com.example.enums.ProfileRole;
import com.example.service.TagService;
import com.example.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;
    @PostMapping("")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authorization,
                                    @RequestBody TagDTO dto) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(tagService.create(dto));
    }
    @PostMapping("/{id}")
    public ResponseEntity<?>update(@PathVariable("id")Integer id,
                                   @RequestHeader("Authorization")String authorization,
                                   @RequestBody TagRequestDTO dto){
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(tagService.update(id,dto));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?>delete(@PathVariable("id")Integer id,
                                   @RequestHeader("Authorization")String authorization){
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(tagService.delete(id));
    }
    @GetMapping("")
    public ResponseEntity<List<TagDTO>>getAll(@RequestHeader("Authorization")String authorization){
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(tagService.getAll());
    }
}
