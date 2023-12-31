package com.example.spotitube.spotitubeapp.resources;

import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.interfaces.ILoginService;
import com.example.spotitube.spotitubeapp.resources.interfaces.IPlaylistService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistResource {

    private IPlaylistService playlistService;
    private ILoginService loginService;

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
        playlistService.addPlaylist(playlist, userID);
        return Response
                .status(201)
                .entity(playlistService.getAllPlaylists(userID))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(PlaylistDTO playlist, @PathParam("id") int id, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        playlistService.editPlaylist(playlist, id);
        return Response
                .status(200)
                .entity(playlistService.getAllPlaylists(loginService.getUserID(token)))
                .build();

    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        playlistService.deletePlaylist(id);
        return Response
                .status(200)
                .entity(playlistService.getAllPlaylists(loginService.getUserID(token)))
                .build();
    }

    @GET
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response addTrackToPlaylist(@PathParam("id") int playlistId, TrackDTO trackDTO, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        playlistService.addTrackToPlaylist(trackDTO, playlistId);
        return Response
                .status(201)
                .entity(playlistService.getTracksPerPlaylist(playlistId))
                .build();
    }

    @DELETE
    @Path("/{id}/tracks/{trackId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@PathParam("id") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        playlistService.removeTrackFromPlaylist(trackId, playlistId);
        return Response
                .status(200)
                .entity(playlistService.getTracksPerPlaylist(playlistId))
                .build();
    }

    @Inject
    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    @Inject
    public void setPlaylistService(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }
}
