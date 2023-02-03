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
        albumField = "album",
        message = "{songDtoRequest.uniquesong}"
)
public class SongDtoRequest {

    @NotBlank(message = "{songDtoRequest.title.notblank}")
    private String title;

    @NotNull(message = "{songDtoRequest.artistId.notnull}")
    private Long artistId;

    @NotBlank(message = "{songDtoRequest.album.notblank}")
    private String album;

    @NotBlank(message = "{songDtoRequest.year.notblank}")
    private String year;




}
