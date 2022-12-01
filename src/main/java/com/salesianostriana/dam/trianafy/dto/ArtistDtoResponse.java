package com.salesianostriana.dam.trianafy.dto;


import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistDtoResponse {

    @JsonView(value = {View.SongView.songDtoResponseByIdView.class})
    private Long id;

    @JsonView(value = {View.SongView.songDtoResponseByIdView.class})
    private String artist;
}
