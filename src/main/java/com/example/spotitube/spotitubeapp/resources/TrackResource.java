package com.example.spotitube.spotitubeapp.resources;

import com.example.spotitube.spotitubeapp.resources.interfaces.ILoginService;
import com.example.spotitube.spotitubeapp.resources.interfaces.ITrackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tracks")
public class TrackResource {

    private ILoginService loginService;

    private ITrackService trackService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksNotInPlaylist(@QueryParam("token") String token, @QueryParam("forPlaylist") int id) {
        loginService.verifyToken(token);
        return Response
                .status(200)
                .entity(trackService.getTracksNotInPlaylist(id))
                .build();
    }

    @Inject
    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    @Inject
    public void setTrackService(ITrackService trackService) {
        this.trackService = trackService;
    }

}
