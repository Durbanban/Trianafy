package com.salesianostriana.dam.trianafy.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.dto.*;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import com.salesianostriana.dam.trianafy.view.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Canción ", description = "Este es el controlador de canciones")
public class SongController {

    private final SongService songService;

    private final PlaylistService playlistService;

    private final ArtistService artistService;

    private final SongDtoConverter songDtoConverter;


    @Operation(summary = "Obtiene todas las canciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se han encontrado canciones",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SongDtoResponse.class)),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 4,
                                                    "title": "19 días y 500 noches",
                                                    "album": "19 días y 500 noches",
                                                    "year": "1999",
                                                    "artist": "Joaquín Sabina"
                                                },
                                                {
                                                    "id": 5,
                                                    "title": "Donde habita el olvido",
                                                    "album": "19 días y 500 noches",
                                                    "year": "1999",
                                                    "artist": "Joaquín Sabina"
                                                },
                                                {
                                                    "id": 6,
                                                    "title": "A mis cuarenta y diez",
                                                    "album": "19 días y 500 noches",
                                                    "year": "1999",
                                                    "artist": "Joaquín Sabina"
                                                }
                                            ]
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se han encontrado canciones",
            content = @Content)
    })
    @JsonView(value = {View.SongView.songDtoResponseView.class})
    @GetMapping("/song/")
    public ResponseEntity<List<SongDto>> getAllSongs() {
        if(songService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            List<SongDto> result = songService.findAll()
                    .stream()
                    .map(SongDtoConverter::of)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }
    @Operation(summary = "Obtiene una canción en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se ha encontrado la canción",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SongDtoResponseById.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 6,
                                                "title": "A mis cuarenta y diez",
                                                "album": "19 días y 500 noches",
                                                "year": "1999",
                                                "artist": {
                                                    "id": 1,
                                                    "artist": "Joaquín Sabina"
                                                }
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se ha encontrado la canción por el ID",
            content = @Content)
    })
    @JsonView(value = View.SongView.songDtoResponseByIdView.class)
    @GetMapping("/song/{id}")
    public ResponseEntity<SongDto> getSongById(
            @Parameter(
                    description = "ID de la canción a buscar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        if(songService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(songDtoConverter.of(songService.findById(id).get()));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Crea una canción")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
    description = "Cuerpo de la petición",
    content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = SongDtoRequest.class),
            examples = {
                    @ExampleObject(
                            value = """
                                    {
                                        "title": "Levitating",
                                        "artistId": 2,
                                        "album": "Future Nostalgia",
                                        "year": "2019"
                                    }
                                    """
                    )
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
            description = "Se ha creado la canción",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SongDtoResponse.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 14,
                                                "title": "Levitating",
                                                "album": "Future Nostalgia",
                                                "year": "2019",
                                                "artist": "Dua Lipa"
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "400",
            description = "Los datos de la canción son incorrectos",
            content = @Content)
    })
    @JsonView(value = {View.SongView.songDtoResponseView.class})
    @PostMapping("/song/")
    public ResponseEntity<SongDto> createSong(@RequestBody SongDtoRequest songDtoRequest) {

        if(songDtoRequest.getArtistId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Song song = songDtoConverter.toSong(songDtoRequest);
        Artist artist = artistService.findById(songDtoRequest.getArtistId()).orElse(null);
        if(artist != null) {
            song.setArtist(artist);
        }

        if(artistService.existsById(songDtoRequest.getArtistId())) {
            if(song.getYear() == null || song.getAlbum() == null || song.getTitle() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }else {
                return ResponseEntity.status(HttpStatus.CREATED).body(songDtoConverter.of(songService.add(song)));
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @Operation(summary = "Edita una canción en base a su ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
    description = "Cuerpo de la petición",
    content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = SongDtoRequest.class),
            examples = {
                    @ExampleObject(
                            value = """
                                    {
                                        "title": "Dieguitos y Mafaldas",
                                        "artistId": 1,
                                        "album": "19 días y 500 noches",
                                        "year": "1999"
                                    }
                                    """
                    )
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se ha editado la canción",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SongDtoResponse.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 13,
                                                "title": "Dieguitos y Mafaldas",
                                                "album": "19 días y 500 noches",
                                                "year": "1999",
                                                "artist": "Joaquín Sabina"
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "400",
            description = "Los datos de la canción son incorrectos",
            content = @Content),
            @ApiResponse(responseCode = "404",
            description = "No se ha encontrado la canción",
            content = @Content)
    })
    @JsonView(value = {View.SongView.songDtoResponseView.class})
    @PutMapping("/song/{id}")
    public ResponseEntity<SongDto> editSong(
            @Parameter(
                    description = "ID de la canción a editar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id, @RequestBody SongDtoRequest songDtoRequest) {

        if(songDtoRequest.getArtistId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Song song = songDtoConverter.toSong(songDtoRequest);
        Artist artist = artistService.findById(songDtoRequest.getArtistId()).orElse(null);
        song.setArtist(artist);

        if(!songService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else if(song.getArtist() == null
                || song.getTitle() == null
                || song.getYear() == null
                || song.getAlbum() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.of(songService.findById(id).map(toEdit -> {
                toEdit.setYear(song.getYear());
                toEdit.setAlbum(song.getAlbum());
                toEdit.setTitle(song.getTitle());
                toEdit.setArtist(artist);
                return Optional.of(songDtoConverter.of(songService.edit(toEdit)));
            }).orElse(Optional.empty()));
        }

    }
    @Operation(summary = "Borra una canción en base a su ID")
    @ApiResponse(responseCode = "204",
    description = "Se ha borrado la canción",
    content = @Content)
    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> deleteSong(
            @Parameter(
                    description = "ID de la canción a borrar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        if(songService.existsById(id)) {
            List<Playlist> playlistsWithSong = playlistService.findPlaylistBySong(songService.findById(id).get());
            playlistsWithSong
                    .stream().forEach(playlist -> {
                        while(playlist.getSongs().contains(songService.findById(id).get())) {
                            playlist.deleteSong(songService.findById(id).get());
                        }
                    });
            songService.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
