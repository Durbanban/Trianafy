package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDtoRequest {

    @NotBlank(message = "{song.title.notblank}")
    private String title;


    private Long artistId;

    @NotBlank(message = "{song.album.notblank}")
    private String album;

    @NotBlank(message = "{song.year.notblank}")
    private String year;




}
