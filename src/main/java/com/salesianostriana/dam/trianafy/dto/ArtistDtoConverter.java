package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistDtoConverter {

    public static ArtistDtoResponse toArtistDto(Artist artist) {
        return ArtistDtoResponse
                .builder()
                .id(artist.getId())
                .artist(artist.getName())
                .build();
    }
}
