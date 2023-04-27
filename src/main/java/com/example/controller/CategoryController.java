package com.example.controller;

import com.example.dto.*;
import com.example.dto.category.CategoryDTO;
import com.example.dto.category.CategoryRequestDTO;
import com.example.enums.ProfileRole;
import com.example.exception.MethodNotAllowedException;
import com.example.service.CategoryService;
import com.example.util.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private Container container = new Container();

    @PostMapping("")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
                                              @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed !!!");
        }
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id,
                                              @RequestBody CategoryDTO dto,
                                              @RequestHeader("authorization") String authorization) {
        JwtDTO jwtDTO = container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(categoryService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("method not allowed !");
        }
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAll(@RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("method not allowed !");
        }
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("get-by-lang") ///uz,ru,en
    private ResponseEntity<List<CategoryRequestDTO>> getByLang(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(categoryService.getByLang(lang));
    }
}
