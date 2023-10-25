package com.example.spotitube.spotitubeapp.exceptions.mapper;

import com.example.spotitube.spotitubeapp.exceptions.SpotitubeException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SpotitubeExceptionMapper implements ExceptionMapper<SpotitubeException> {
    @Override
    public Response toResponse(SpotitubeException exception) {
        return Response
                .status(exception.getStatus())
                .entity(exception.getMessage())
                .build();
    }
}
