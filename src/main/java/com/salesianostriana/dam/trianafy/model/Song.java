package com.salesianostriana.dam.trianafy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String title;

    private String album;

    @Column(name = "year_of_song")
    private String year;

    @ManyToOne(fetch = FetchType.EAGER)
    private Artist artist;


}
