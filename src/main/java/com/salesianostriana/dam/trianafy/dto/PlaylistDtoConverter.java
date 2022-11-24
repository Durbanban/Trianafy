package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlaylistDtoConverter {


    public Playlist toPlaylist(PlaylistDtoRequest playlistDtoRequest) {
        List<Song> lista = new ArrayList<Song>();
        return Playlist
                .builder()
                .name(playlistDtoRequest.getName())
                .description(playlistDtoRequest.getDescription())
                .songs(lista)
                .build();

    }

    public PlaylistDtoResponseAll toPlaylistDto(Playlist playlist) {
        return PlaylistDtoResponseAll
                .builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .numberOfSongs(playlist.getSongs().size())
                .build();
    }

    public PlaylistDtoResponseCreation toPlayListDtoCreation(Playlist playlist) {
        return PlaylistDtoResponseCreation
                .builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .build();
    }
}
