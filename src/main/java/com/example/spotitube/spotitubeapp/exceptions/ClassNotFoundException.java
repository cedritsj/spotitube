package com.example.spotitube.spotitubeapp.exceptions;

public class ClassNotFoundException extends SpotitubeException {
    public ClassNotFoundException(String message) {
        super(message, 404);
    }
}