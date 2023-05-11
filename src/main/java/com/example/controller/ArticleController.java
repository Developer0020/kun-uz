package com.example.controller;

import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleFullInfoDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.dto.article.ArticleRequestDTO;
import com.example.dto.article.ArticleShortInfoDTO;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<ArticleRequestDTO> create(@RequestBody @Valid ArticleRequestDTO dto,
                                                    HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //todo
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);

        JwtUtil.checkForRequiredRole(request, ProfileRole.MODERATOR);
        Integer prtId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(articleService.create(dto, prtId));
    }

    @PostMapping("/private/{id}")
    public ResponseEntity<ArticleRequestDTO> update(@RequestBody ArticleRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") String id) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, jwt.getId(), id));
    }

    @DeleteMapping("/private/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/private/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestParam String status,
                                          @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status), id));
    }

    /*Get Last 5 Article By Types  ordered_by_created_date
            (Berilgan types bo'yicha oxirgi 5ta pubished bo'lgan article ni return qiladi.)
            ArticleShortInfo*/
    @GetMapping("/public/get-last-5")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast5(@RequestParam("type_id") Integer id) {
        return ResponseEntity.ok(articleService.getLastByCount(id, 5));
    }

    @GetMapping("/public/get-last-3")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast3(@RequestParam("type_id") Integer id) {
        return ResponseEntity.ok(articleService.getLastByCount(id, 3));
    }

    /*    7. Get Last 8  Articles witch id not included in given list.
     */
    @PostMapping("/public/get-last-given-list")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast8(@RequestBody List<Integer> countList) {
        return ResponseEntity.ok(articleService.getLastGivenList(countList));
    }
//
//    @GetMapping()
//    public ResponseEntity<ArticleFullInfoDTO>

    @GetMapping("/get_by_id_and_lang")
    public ResponseEntity<ArticleFullInfoDTO> articleFullInfo(@RequestParam("id") String id,
                                                              @RequestParam("lang") String lang) {
        ArticleFullInfoDTO dto = articleService.articleFullInfo(id, lang);
        return ResponseEntity.ok(dto);
    }

    // 9
    @GetMapping("/4-article-by-types")
    public ResponseEntity<List<ArticleShortInfoDTO>> get4ArticleByTypes(@RequestParam("typeId") Integer typeId,
                                                                        @RequestParam("id") String id) {
        List<ArticleShortInfoDTO> list = articleService.get4ArticleByTypes(typeId, id);
        return ResponseEntity.ok(list);
    }

    // 10
    @GetMapping("/4most_read")
    public ResponseEntity<List<ArticleShortInfoDTO>> articleShortInfo() {
        List<ArticleShortInfoDTO> dtoList = articleService.articleShortInfo();
        return ResponseEntity.ok(dtoList);
    }

    // 12
    @GetMapping("/get-by-type-and-region")
    public ResponseEntity<List<ArticleShortInfoDTO>> articleShortInfo(@RequestParam("type") String type,
                                                                      @RequestParam("region") String regionName) {
        List<ArticleShortInfoDTO> dtoList = articleService.articleShortInfo(type, regionName);
        return ResponseEntity.ok(dtoList);
    }

    // 13
    @GetMapping("/region-article")
    public ResponseEntity<List<ArticleShortInfoDTO>> getRegionArticle(@RequestParam("id") Integer id, @RequestParam("size") int size,
                                                                      @RequestParam("page") int page) {
        List<ArticleShortInfoDTO> list = articleService.getRegionArticle(id, size, page);
        return ResponseEntity.ok(list);
    }

    // 14
    @GetMapping("/5-category-article/{id}")
    public ResponseEntity<List<ArticleShortInfoDTO>> get5CategoryArticle(@PathVariable Integer id) {
        List<ArticleShortInfoDTO> list = articleService.get5CategoryArticle(id);
        return ResponseEntity.ok(list);
    }

    // 15
    @GetMapping("/category-article")
    public ResponseEntity<List<ArticleShortInfoDTO>> getCategoryArticle(@RequestParam("id") Integer id, @RequestParam("size") int size,
                                                                        @RequestParam("page") int page) {
        List<ArticleShortInfoDTO> list = articleService.getCategoryArticle(id, size, page);
        return ResponseEntity.ok(list);
    }

    // 16
    @PutMapping("/articleViewCount/{id}")
    public ResponseEntity<ArticleDTO> articleViewCount(@PathVariable String id) {
        return ResponseEntity.ok(articleService.articleViewCount(id));
    }

    // 17
    @PutMapping("/articleShareCount/{id}")
    public ResponseEntity<ArticleDTO> articleShareCount(@PathVariable String id) {
        return ResponseEntity.ok(articleService.articleShareCount(id));
    }
}
