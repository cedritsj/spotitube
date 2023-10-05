package com.example.spotitube.spotitubeapp.resources;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.services.LoginService;
import com.example.spotitube.spotitubeapp.services.PlaylistService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistResource {

    @Inject
    private PlaylistService playlistService;
    @Inject
    private LoginService loginService;

    @Inject
    private PlaylistDAO playlistDAO;

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
    public Response editPlaylist(PlaylistDTO playlist, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        try {
            playlistService.editPlaylist(playlist);
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
    public Response deletePlaylist(PlaylistDTO playlist, @PathParam("id") int id, @QueryParam("token") String token) {
        loginService.verifyToken(token);
        try {
            playlistService.deletePlaylist(playlist);
            return Response
                    .status(200)
                    .entity(playlistService.getAllPlaylists(loginService.getUserID(token)))
                    .build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }
}
