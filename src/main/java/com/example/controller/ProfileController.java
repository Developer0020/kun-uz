package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.enums.ProfileRole;
import com.example.exception.MethodNotAllowedException;
import com.example.service.ProfileService;
import com.example.util.Container;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    private Container container = new Container();

    @PostMapping({"", "/"})
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ProfileDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(dto,id));
    }

    @PostMapping("/update-detail")
    public ResponseEntity<?> updateDetail(@RequestBody ProfileDTO dto,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = container.authorization(authorization);
        return ResponseEntity.ok(profileService.updateDetail(dto, jwtDTO.getId()));
    }

    @GetMapping("")
    public ResponseEntity<List<ProfileDTO>> getAll(@RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("method not allowed !");
        }
        return ResponseEntity.ok(profileService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getById(@PathVariable("id") Integer id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("method not allowed !");
        }
        return ResponseEntity.ok(profileService.delete(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<ProfileDTO>> pagination(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        return null;
    }

}
