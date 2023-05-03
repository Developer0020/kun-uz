package com.example.controller;

import com.example.dto.SavedArticleDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.service.SavedArticleService;
import com.example.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/saved-article")
public class SavedArticleController {
    public final SavedArticleService savedArticleService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody String articleId,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(savedArticleService.create(articleId, jwtDTO.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable("id")Integer id){
        return ResponseEntity.ok(savedArticleService.delete(id));
    }
    @GetMapping("")
    public ResponseEntity<List<SavedArticleDTO>>list(){
        return ResponseEntity.ok(savedArticleService.list());
    }
}
/*10. SavedArticle
    1. Create (ANY)
        article_id
    2. Delete (ANY)
        article_id
    3. Get Profile Saved Article List (ANY)
        (id,article(id,title,description,image(id,url)))
11. Attach*/