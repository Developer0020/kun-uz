package com.example.controller;

import com.example.exception.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController  {
    @ExceptionHandler({AppBadRequestException.class, ArticleTypeNotFoundException.class,
            CategoryNotFoundException.class, ItemNotFoundException.class,
            MethodNotAllowedException.class, RegionNotFoundException.class, ArticleNotFoundException.class,
            ProfileNotFoundException.class, AttachNotFoundException.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
