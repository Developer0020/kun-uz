package com.example.controller;


import com.example.dto.category.CategoryDTO;
import com.example.dto.category.CategoryRequestDTO;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<CategoryDTO> create(@RequestBody @Valid CategoryDTO dto,
                                              @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody CategoryDTO dto,
                                    @RequestHeader("authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(dto, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.delete(id));
    }
    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAll(@RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getAll());
    }
    @GetMapping("get-by-lang") ///uz,ru,en
    private ResponseEntity<List<CategoryRequestDTO>> getByLang(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(categoryService.getByLang(lang));
    }
}
