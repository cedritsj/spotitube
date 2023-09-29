package com.example.spotitube.spotitubeapp.resources;

import com.example.spotitube.spotitubeapp.services.LoginService;
import com.example.spotitube.spotitubeapp.services.PlaylistService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistResource {

    @Inject
    private PlaylistService playlistService;
    @Inject
    private LoginService loginService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        loginService.verifyToken(token);
        return Response
                .status(200)
                .entity(playlistService.getAllPlaylists(token))
                .build();
    }
}
