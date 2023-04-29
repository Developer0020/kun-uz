package com.example.controller;

import com.example.dto.articleType.ArticleTypeDTO;
import com.example.dto.articleType.ArticleTypeRequestDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article-type")
@AllArgsConstructor
public class ArticleTypeController {

    private final ArticleTypeService articleTypeService;
    @PostMapping("")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody @Valid ArticleTypeDTO dto,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.create(dto));
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                                 @RequestBody ArticleTypeDTO dto,
                                                 @RequestHeader("authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.update(dto,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.delete(id));
    }
    @GetMapping("")
    public ResponseEntity<List<ArticleTypeDTO>> getAll(@RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.getAll());
    }
    @GetMapping("/get-by-lang") ///uz,ru,en
    private ResponseEntity<List<ArticleTypeRequestDTO>>getByLang(@RequestParam("lang")String lang){
        return ResponseEntity.ok(articleTypeService.getByLang(lang));
    }

    /* 5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)*/
}
