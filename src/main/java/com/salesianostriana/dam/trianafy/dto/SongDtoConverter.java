package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongDtoConverter {

    public Song toSong(SongDtoRequest dto) {
        return Song
                .builder()
                .title(dto.getTitle())
                .album(dto.getAlbum())
                .year(dto.getYear())
                .build();
    }

    public static SongDtoResponse toSongDto(Song song) {
        String artistName = "";
        if(song.getArtist() != null) {
            artistName = song.getArtist().getName();
        }
        return SongDtoResponse
                .builder()
                .id(song.getId())
                .title(song.getTitle())
                .album(song.getAlbum())
                .year(song.getYear())
                .artist(artistName)
                .build();
    }

    public SongDtoResponseById toSongDtoById(Song song) {
        ArtistDtoResponse artist = new ArtistDtoResponse();
        if(song.getArtist() != null) {
            artist = ArtistDtoConverter.toArtistDto(song.getArtist());
        }
        return SongDtoResponseById
                .builder()
                .id(song.getId())
                .title(song.getTitle())
                .album(song.getAlbum())
                .year(song.getYear())
                .artist(artist)
                .build();
    }

    public static SongDto of(Song song) {
        ArtistDtoResponse artist = new ArtistDtoResponse();
        if(song.getArtist() != null) {
            artist = ArtistDtoConverter.toArtistDto(song.getArtist());
        }
        return SongDto
                .builder()
                .id(song.getId())
                .title(song.getTitle())
                .year(song.getYear())
                .album(song.getAlbum())
                .artistDto(artist)
                .artistId(song.getArtist().getId())
                .artist(song.getArtist().getName())
                .build();
    }

}
