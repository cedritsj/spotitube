package com.example.spotitube.spotitubeapp.resources;

import com.example.spotitube.spotitubeapp.services.LoginService;
import com.example.spotitube.spotitubeapp.services.TrackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class TrackResource {

    private LoginService loginService;

    private TrackService trackService;

    @GET
    @Path("/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksNotInPlaylist(@QueryParam("token") String token, @QueryParam("forPlaylist") int id) {
        loginService.verifyToken(token);
        return Response
                .status(200)
                .entity(trackService.getTracksNotInPlaylist(id))
                .build();
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

}
