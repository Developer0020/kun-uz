package com.example.exception;

public class RegionNotFoundException extends RuntimeException{
    public RegionNotFoundException(String text) {
        super(text);
    }
}
