package com.example.spotitube.spotitubeapp.exceptions;

public class SpotitubeException extends RuntimeException {
    private final int status;
    public SpotitubeException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}