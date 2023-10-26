package com.example.spotitube.spotitubeapp.exceptions;

public class InvalidCredentialsException extends SpotitubeException {
    public InvalidCredentialsException() {
        super("Invalid credentials", 403);
    }
}