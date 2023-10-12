package com.example.spotitube.spotitubeapp.exceptions;

public class AuthenticationException extends SpotitubeException{
    public AuthenticationException() {
        super("Authentication failed", 401);
    }
}