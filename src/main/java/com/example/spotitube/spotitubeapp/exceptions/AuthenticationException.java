package com.example.spotitube.spotitubeapp.exceptions;

public class AuthenticationException extends SpotitubeException{
    public AuthenticationException() {
        super("User does not have a token", 401);
    }
}