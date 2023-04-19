package com.example.controller;

import com.example.dto.*;
import com.example.enums.ProfileRole;
import com.example.exception.MethodNotAllowedException;
import com.example.service.RegionService;
import com.example.util.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("region")
public class RegionController {
    @Autowired
    private RegionService regionService;
    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = Container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed !!!");
        }
        return ResponseEntity.ok(regionService.create(dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<RegionDTO> update(@PathVariable("id") Integer id,
                                                 @RequestBody RegionDTO dto,
                                                 @RequestHeader("authorization") String authorization) {
        JwtDTO jwtDTO = Container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(regionService.update(dto,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = Container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("method not allowed !");
        }
        return ResponseEntity.ok(regionService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<List<RegionDTO>> getAll(@RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = Container.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("method not allowed !");
        }
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("get-by-lang") ///uz,ru,en
    private ResponseEntity<List<RegionRequestDTO>>getByLang(@RequestParam("lang")String lang){
        return ResponseEntity.ok(regionService.getByLang(lang));
    }
}
