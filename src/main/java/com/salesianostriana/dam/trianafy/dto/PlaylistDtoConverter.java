package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaylistDtoConverter {


    public Playlist toPlaylist(PlaylistDtoRequest playlistDtoRequest) {
        List<Song> lista = new ArrayList<>();
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

    public PlaylistDtoResponseAllSongs toPlayListDtoAllSongs(Playlist playlist) {
        List<SongDtoResponse> list = playlist.getSongs()
                .stream()
                .map(SongDtoConverter::toSongDto)
                .collect(Collectors.toList());
        return PlaylistDtoResponseAllSongs
                .builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .songs(list)
                .build();
    }
}
