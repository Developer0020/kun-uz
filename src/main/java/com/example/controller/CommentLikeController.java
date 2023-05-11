package com.example.controller;

import com.example.dto.jwt.JwtDTO;
import com.example.enums.CommentLikeStatus;
import com.example.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment-like")
@AllArgsConstructor
public class CommentLikeController {
//    private final CommentLikeStatus;
////    @GetMapping("/like/{comment_id}")
////public ResponseEntity<?>like(@PathVariable("comment_id") Integer commentId,
////                             @RequestHeader("Authorization")String authorization){
////        JwtDTO jwtDTO= JwtUtil.getJwtDTO(authorization);
////        return ResponseEntity.ok()
////    }
}
