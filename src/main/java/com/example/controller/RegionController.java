package com.example.controller;

import com.example.dto.region.RegionDTO;
import com.example.dto.region.RegionRequestDTO;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
@AllArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@RequestBody @Valid RegionDTO dto,
                                            @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto));
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody RegionDTO dto,
                                    @RequestHeader("authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.update(dto, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.delete(id));
    }
    @GetMapping("")
    public ResponseEntity<List<RegionDTO>> getAll(@RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.getAll());
    }
    @GetMapping("get-by-lang") ///uz,ru,en
    private ResponseEntity<List<RegionRequestDTO>> getByLang(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(regionService.getByLang(lang));
    }
}
