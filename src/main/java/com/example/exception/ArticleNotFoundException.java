package com.example.exception;

public class ArticleNotFoundException extends RuntimeException{
    public ArticleNotFoundException(String text) {
        super(text);
    }
}
