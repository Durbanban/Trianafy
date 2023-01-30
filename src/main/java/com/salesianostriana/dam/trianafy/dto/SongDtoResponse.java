package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDtoResponse {

    private Long id;

    @NotEmpty
    private String title;

    @NotBlank
    private String album;

    @NotBlank
    private String year;

    @NotBlank
    private String artist;


}
