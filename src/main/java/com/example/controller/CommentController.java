package com.example.controller;

import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentRequestDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.service.CommentService;
import com.example.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CommentRequestDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentService.create(dto, jwtDTO.getId()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer commentId,
                                    @RequestBody CommentRequestDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentService.update(commentId, dto, jwtDTO.getId()));

    }

    @GetMapping("/{article_id}")
    public ResponseEntity<List<CommentDTO>> getByArticleList(@PathVariable("article_id") String articleId) {
        return ResponseEntity.ok(commentService.getByArticleCommentList(articleId));
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<CommentDTO>> paging(@RequestHeader("Authorization") String authorization,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "30") int size) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.list(page,size));
    }
}
