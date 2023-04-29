package com.example.exception;

public class ProfileNotFoundException extends RuntimeException{
    public ProfileNotFoundException(String text) {
        super(text);
    }
}
