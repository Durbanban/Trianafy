package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistDtoRequest {

    @NotBlank(message = "{playlistDtoRequest.name.notblank}")
    private String name;

    @NotNull(message = "{playlistDtoRequest.description.notnull}")
    private String description;
}
