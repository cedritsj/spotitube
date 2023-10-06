package com.example.spotitube.spotitubeapp.exceptions;

public class DatabaseException extends SpotitubeException {
    public DatabaseException(String message) {
        super(message, 500);
    }
}