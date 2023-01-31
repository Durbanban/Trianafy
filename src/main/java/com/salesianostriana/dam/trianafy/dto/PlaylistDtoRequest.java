package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistDtoRequest {

    @NotBlank(message = "{playlist.name.notblank}")
    private String name;

    private String description;
}
