package com.example.spotitube.spotitubeapp.resources.interfaces;

import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;

public interface IPlaylistService {
    PlaylistResponseDTO getAllPlaylists(int userID);

    void addPlaylist(PlaylistDTO playlist, int userID);

    void editPlaylist(PlaylistDTO playlist, int id);

    void deletePlaylist(int id);

    TrackResponseDTO getTracksPerPlaylist(int id);

    void addTrackToPlaylist(TrackDTO trackDTO, int playlistId);

    void removeTrackFromPlaylist(int trackId, int playlistId);
}
