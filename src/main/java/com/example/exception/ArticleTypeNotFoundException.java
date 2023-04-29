package com.example.exception;

public class ArticleTypeNotFoundException extends RuntimeException{
    public ArticleTypeNotFoundException(String text) {
        super(text);
    }
}
