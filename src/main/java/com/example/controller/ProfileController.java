package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterRequestDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @PostMapping({"", "/"})
    public ResponseEntity<ProfileDTO> create(@RequestBody @Valid ProfileDTO dto,
                                             @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto));
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ProfileDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(dto,id));
    }

    @PostMapping("/update-detail")
    public ResponseEntity<?> updateDetail(@RequestBody ProfileDTO dto,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt=JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.updateDetail(dto, jwt.getId()));
    }

    @GetMapping("")
    public ResponseEntity<List<ProfileDTO>> getAll(@RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> get(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                        @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.delete(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<ProfileDTO>> pagination(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        return null;
    }
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterRequestDTO filterRequestDTO) {
        List<ProfileDTO> dtoList = profileService.filter(filterRequestDTO);
        return ResponseEntity.ok(dtoList);
    }
}
