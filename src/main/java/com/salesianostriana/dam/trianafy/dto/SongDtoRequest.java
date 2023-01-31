package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.validation.annotation.UniqueSong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UniqueSong(
        titleField = "title",
        artistIdField = "artistId",
        message = "{song.uniquesong}"
)
public class SongDtoRequest {

    @NotBlank(message = "{song.title.notblank}")
    private String title;

    @NotNull(message = "{song.artistId.notnull}")
    private Long artistId;

    @NotBlank(message = "{song.album.notblank}")
    private String album;

    @NotBlank(message = "{song.year.notblank}")
    private String year;




}
