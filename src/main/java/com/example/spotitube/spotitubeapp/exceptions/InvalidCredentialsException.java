package com.example.spotitube.spotitubeapp.exceptions;

import com.example.spotitube.spotitubeapp.exceptions.SpotitubeException;

public class InvalidCredentialsException extends SpotitubeException {
    public InvalidCredentialsException() {
        super("Invalid credentials", 403);
    }
}