package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.ArticleTypeRequestDTO;
import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.enums.ProfileRole;
import com.example.exception.MethodNotAllowedException;
import com.example.service.ArticleTypeService;
import com.example.util.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article-type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO dto,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = Container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed !!!");
        }
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ArticleTypeDTO> update(@PathVariable("id") Integer id,
                                                 @RequestBody ArticleTypeDTO dto,
                                                 @RequestHeader("authorization") String authorization) {
        JwtDTO jwtDTO = Container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleTypeService.update(dto,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = Container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("method not allowed !");
        }
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<List<ArticleTypeDTO>> getAll(@RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = Container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("method not allowed !");
        }
        return ResponseEntity.ok(articleTypeService.getAll());
    }

    @GetMapping("get-by-lang") ///uz,ru,en
    private ResponseEntity<List<ArticleTypeRequestDTO>>getByLang(@RequestParam("lang")String lang){
        return ResponseEntity.ok(articleTypeService.getByLang(lang));
    }

    /* 5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)*/
}
