package com.example.spotitube.spotitubeapp.exceptions;

public class FileNotFoundException extends SpotitubeException {
    public FileNotFoundException(String message) {
        super(message, 404);
    }
}