package com.salesianostriana.dam.trianafy.dto;


import com.salesianostriana.dam.trianafy.validation.annotation.UniqueArtistName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistDtoResponse {

    private Long id;

    private String artist;
}
