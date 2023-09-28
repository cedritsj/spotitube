package com.example.spotifytubeapp.exceptions;

import com.example.spotitube.spotitubeapp.exceptions.SpotitubeException;

public class InvalidCredentialsException extends SpotitubeException {
    public InvalidCredentialsException() {
        super("Invalid credentials", 403);
    }
}