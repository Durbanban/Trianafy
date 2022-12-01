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
public class SongDto {

    @JsonView({View.SongView.songDtoResponseByIdView.class,
            View.SongView.songDtoResponseView.class})
    private Long id;


    @JsonView({View.SongView.SongDtoRequestView.class,
            View.SongView.songDtoResponseView.class,
            View.SongView.songDtoResponseByIdView.class})
    private String title;

    @JsonView(View.SongView.SongDtoRequestView.class)
    private Long artistId;


    @JsonView({View.SongView.SongDtoRequestView.class,
            View.SongView.songDtoResponseView.class,
            View.SongView.songDtoResponseByIdView.class})
    private String album;

    @JsonView({View.SongView.SongDtoRequestView.class,
            View.SongView.songDtoResponseView.class,
            View.SongView.songDtoResponseByIdView.class})
    private String year;

    @JsonView(View.SongView.songDtoResponseView.class)
    private String artist;

    @JsonView(View.SongView.songDtoResponseByIdView.class)
    private ArtistDtoResponse artistDto;




}
