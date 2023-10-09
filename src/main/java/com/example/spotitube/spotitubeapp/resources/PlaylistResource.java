package com.example.spotitube.spotitubeapp.resources;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.services.LoginService;
import com.example.spotitube.spotitubeapp.services.PlaylistService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistResource {

    private PlaylistService playlistService;
    private LoginService loginService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        loginService.verifyToken(token);
        return Response
                .status(200)
                .entity(playlistService.getAllPlaylists(loginService.getUserID(token)))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(PlaylistDTO playlist, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        int userID = loginService.getUserID(token);
        try {
            playlistService.addPlaylist(playlist, userID);
            return Response
                    .status(201)
                    .entity(playlistService.getAllPlaylists(userID))
                    .build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(PlaylistDTO playlist, @PathParam("id") int id, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        try {
            playlistService.editPlaylist(playlist, id);
            return Response
                    .status(200)
                    .entity(playlistService.getAllPlaylists(loginService.getUserID(token)))
                    .build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        try {
            playlistService.deletePlaylist(id);
            return Response
                    .status(200)
                    .entity(playlistService.getAllPlaylists(loginService.getUserID(token)))
                    .build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/{id}/tracks")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getTracksFromPlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        return Response
                .status(200)
                .entity(playlistService.getTracksPerPlaylist(id))
                .build();
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int id, TrackDTO trackDTO, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        return Response
                .status(200)
                .entity(playlistService.addTrackToPlaylist(id, trackDTO))
                .build();
    }

    @DELETE
    @Path("/{id}/tracks/{trackId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@PathParam("id") int id, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        return Response
                .status(200)
                .entity(playlistService.removeTrackFromPlaylist(id, trackId))
                .build();
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }
}
