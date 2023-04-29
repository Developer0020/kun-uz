package com.example.exception;

public class AttachNotFoundException extends RuntimeException{
    public AttachNotFoundException(String text) {
        super(text);
    }
}
