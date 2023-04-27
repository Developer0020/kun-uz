package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleRequestDTO;
import com.example.enums.ArticleStatus;
import com.example.exception.MethodNotAllowedException;
import com.example.service.ArticleService;
import com.example.util.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private Container container;
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<ArticleRequestDTO> create(@RequestBody ArticleRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = container.authorization(authorization);
        if (!jwt.getRole().equals("MODERATOR")) {
            throw new MethodNotAllowedException("method not allowed !!!");
        }
        return ResponseEntity.ok(articleService.create(dto, jwt.getId()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ArticleRequestDTO> update(@RequestBody ArticleRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") Integer id) {
        JwtDTO jwt = container.authorization(authorization);
        if (!jwt.getRole().equals("MODERATOR")) {
            throw new MethodNotAllowedException("method not allowed !!!");
        }
        return ResponseEntity.ok(articleService.update(dto, jwt.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = container.authorization(authorization);
        if (!jwt.getRole().equals("MODERATOR")) {
            throw new MethodNotAllowedException("method not allowed !!!");
        }
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id,
                                                   @RequestParam String status,
                                                   @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = container.authorization(authorization);
        if (!jwt.getRole().equals("MODERATOR")) {
            throw new MethodNotAllowedException("method not allowed !!!");
        }
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status),id));
    }
}
