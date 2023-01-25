package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.*;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Lista de reproducción", description = "Este es el controlador de listas de reproducción")
public class PlaylistController {

    private final PlaylistService playlistService;

    private final PlaylistDtoConverter playlistDtoConverter;

    private final SongService songService;

    private final SongDtoConverter songDtoConverter;

    @Operation(summary = "Obtiene todas las listas de reproducción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se han encontrado listas de reproducción",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlaylistDtoResponseAll.class)),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 12,
                                                    "name": "Random",
                                                    "numberOfSongs": 4
                                                },
                                                {
                                                    "id": 13,
                                                    "name": "Lista molona",
                                                    "numberOfSongs": 0
                                                }
                                            ]
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se han encontrado listas de reproducción",
            content = @Content)
    })
    @GetMapping("/list/")
    public ResponseEntity<List<PlaylistDtoResponseAll>> getAllPlaylists() {
        if(playlistService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            List<PlaylistDtoResponseAll> listDto = playlistService.findAll()
                                                                    .stream()
                                                                    .map(playlistDtoConverter::toPlaylistDto)
                                                                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(listDto);

        }
    }

    @Operation(summary = "Obtiene una lista de reproducción en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se ha encontrado la lista de reproducción",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Playlist.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 12,
                                                "name": "Random",
                                                "description": "Una lista muy loca",
                                                "songs": [
                                                    {
                                                        "id": 9,
                                                        "title": "Enter Sandman",
                                                        "album": "Metallica",
                                                        "year": "1991",
                                                        "artist": {
                                                            "id": 3,
                                                            "name": "Metallica"
                                                        }
                                                    },
                                                    {
                                                        "id": 8,
                                                        "title": "Love Again",
                                                        "album": "Future Nostalgia",
                                                        "year": "2021",
                                                        "artist": {
                                                            "id": 2,
                                                            "name": "Dua Lipa"
                                                        }
                                                    },
                                                    {
                                                        "id": 9,
                                                        "title": "Enter Sandman",
                                                        "album": "Metallica",
                                                        "year": "1991",
                                                        "artist": {
                                                            "id": 3,
                                                            "name": "Metallica"
                                                        }
                                                    },
                                                    {
                                                        "id": 11,
                                                        "title": "Nothing Else Matters",
                                                        "album": "Metallica",
                                                        "year": "1991",
                                                        "artist": {
                                                            "id": 3,
                                                            "name": "Metallica"
                                                        }
                                                    }
                                                ]
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se ha encontrado la lista de reproducción",
            content = @Content)
    })
    @GetMapping("/list/{id}")
    public ResponseEntity<PlaylistDtoResponseAllSongs> getPlaylistById(
            @Parameter(
                    description = "ID de la lista de reproducción a buscar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        if(playlistService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(playlistDtoConverter.toPlayListDtoAllSongs(playlistService.findById(id).get()));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Crea una lista de reproducción")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
    description = "Cuerpo de la petición",
    content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlaylistDtoRequest.class),
            examples = {
                    @ExampleObject(
                            value = """
                                    {
                                        "name": "Lista molona",
                                        "description": "Mola mazo"
                                    }
                                    """
                    )
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
            description = "Se ha creado una lista de reproducción",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlaylistDtoResponseCreation.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 13,
                                                "name": "Lista molona",
                                                "description": "Mola mazo"
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "400",
            description = "Los datos de la lista de reproducción son incorrectos",
            content = @Content)
    })
    @PostMapping("/list/")
    public ResponseEntity<PlaylistDtoResponseCreation> createPlaylist(@Valid @RequestBody PlaylistDtoRequest playlistDtoRequest) {
        Playlist playlist = playlistDtoConverter.toPlaylist(playlistDtoRequest);
        if(playlistDtoRequest.getName() == null || playlistDtoRequest.getDescription() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(playlistDtoConverter.toPlayListDtoCreation(playlistService.add(playlist)));
        }

    }
    @Operation(summary = "Edita una lista de reproducción en base a su ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
    description = "Cuerpo de la petición",
    content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlaylistDtoRequest.class),
            examples = {
                    @ExampleObject(
                            value = """
                                    {
                                        "name": "La mejor lista",
                                        "description": "Pues eso"
                                    }
                                    """
                    )
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se ha editado la lista de reproducción",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlaylistDtoResponseAll.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 12,
                                                "name": "La mejor lista",
                                                "numberOfSongs": 4
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "400",
            description = "Los datos de la lista de reproducción son incorrectos",
            content = @Content),
            @ApiResponse(responseCode = "404",
            description = "No se ha encontrado la lista de reproducción",
            content = @Content)
    })
    @PutMapping("/list/{id}")
    public ResponseEntity<PlaylistDtoResponseAll> editPlaylist(
            @Parameter(
                    description = "ID de la lista de reproducción a editar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id, @RequestBody PlaylistDtoRequest playlistDtoRequest) {
        Playlist playlist = playlistDtoConverter.toPlaylist(playlistDtoRequest);
        if(!playlistService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else if(playlist.getName() == null || playlist.getDescription() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.of(playlistService.findById(id)
                    .map(toEdit -> {
                        toEdit.setName(playlist.getName());
                        toEdit.setDescription(playlist.getDescription());
                        return Optional.of(playlistDtoConverter.toPlaylistDto(playlistService.edit(toEdit)));
                    }).orElse(Optional.empty()));
        }
    }
    @Operation(summary = "Borra una lista de reproducción en base a su ID")
    @ApiResponse(responseCode = "204",
    description = "Se ha borrado la lista de reproducción",
    content = @Content)
    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> deletePlaylist(
            @Parameter(
                    description = "ID de la lista de reproducción a borrar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        if(playlistService.existsById(id)) {
            playlistService.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(summary = "Obtiene todas las canciones de una lista de reproducción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se han encontrado canciones",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlaylistDtoResponseAllSongs.class)),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 12,
                                                "name": "Random",
                                                "description": "Una lista muy loca",
                                                "songs": [
                                                    {
                                                        "id": 9,
                                                        "title": "Enter Sandman",
                                                        "album": "Metallica",
                                                        "year": "1991",
                                                        "artist": "Metallica"
                                                    },
                                                    {
                                                        "id": 8,
                                                        "title": "Love Again",
                                                        "album": "Future Nostalgia",
                                                        "year": "2021",
                                                        "artist": "Dua Lipa"
                                                    },
                                                    {
                                                        "id": 9,
                                                        "title": "Enter Sandman",
                                                        "album": "Metallica",
                                                        "year": "1991",
                                                        "artist": "Metallica"
                                                    },
                                                    {
                                                        "id": 11,
                                                        "title": "Nothing Else Matters",
                                                        "album": "Metallica",
                                                        "year": "1991",
                                                        "artist": "Metallica"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se han encontrado canciones en la lista de reproducción",
            content = @Content)
    })
    @GetMapping("/list/{id}/song/")
    public ResponseEntity<PlaylistDtoResponseAllSongs> getAllSongsByPlaylistId(
            @Parameter(
                    description = "ID de la lista de reproducción a la que consultar todas las canciones",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        if(!playlistService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            PlaylistDtoResponseAllSongs playlistDto = playlistDtoConverter.toPlayListDtoAllSongs(playlistService.findById(id).get());
            return ResponseEntity.status(HttpStatus.OK).body(playlistDto);
        }
    }
    @Operation(summary = "Obtiene una canción de una lista de reproducción")
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
                                                "id": 7,
                                                "title": "Don't Start Now",
                                                "album": "Future Nostalgia",
                                                "year": "2019",
                                                "artist": {
                                                    "id": 2,
                                                    "artist": "Dua Lipa"
                                                }
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se ha encontrado la canción o la lista de reproducción",
            content = @Content)
    })
    @Parameters(value = {
            @Parameter(
                    description = "ID de la lista de reproducción a la que consultar la canción",
                    schema = @Schema(implementation = Long.class),
                    name = "id1",
                    required = true
            ),
            @Parameter(
                    description = "ID de la canción a buscar",
                    schema = @Schema(implementation = Long.class),
                    name = "id2",
                    required = true
            )
    })
    @GetMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<SongDtoResponseById> getSongFromPlayList(@PathVariable Long id1, @PathVariable Long id2) {
        if(!playlistService.existsById(id1)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            if(songService.existsById(id2)) {
                if(playlistService.findById(id1).get().getSongs().contains(songService.findById(id2).get())) {
                    return ResponseEntity.status(HttpStatus.OK).body(songDtoConverter.toSongDtoById(songService.findById(id2).get()));
                }else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
    }
    @Operation(summary = "Añade una canción a la lista de reproducción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
            description = "Se ha añadido la canción a la lista de reproducción",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlaylistDtoResponseAllSongs.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 12,
                                                "name": "Random",
                                                "description": "Una lista muy loca",
                                                "songs": [
                                                    {
                                                        "id": 9,
                                                        "title": "Enter Sandman",
                                                        "album": "Metallica",
                                                        "year": "1991",
                                                        "artist": "Metallica"
                                                    },
                                                    {
                                                        "id": 8,
                                                        "title": "Love Again",
                                                        "album": "Future Nostalgia",
                                                        "year": "2021",
                                                        "artist": "Dua Lipa"
                                                    },
                                                    {
                                                        "id": 9,
                                                        "title": "Enter Sandman",
                                                        "album": "Metallica",
                                                        "year": "1991",
                                                        "artist": "Metallica"
                                                    },
                                                    {
                                                        "id": 11,
                                                        "title": "Nothing Else Matters",
                                                        "album": "Metallica",
                                                        "year": "1991",
                                                        "artist": "Metallica"
                                                    },
                                                    {
                                                        "id": 7,
                                                        "title": "Don't Start Now",
                                                        "album": "Future Nostalgia",
                                                        "year": "2019",
                                                        "artist": "Dua Lipa"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se encuentra la canción o la lista de reproducción",
            content = @Content)
    })
    @Parameters(value = {
            @Parameter(
                    description = "ID de la lista de reproducción a la que añadir la canción",
                    schema = @Schema(implementation = Long.class),
                    name = "id1",
                    required = true
            ),
            @Parameter(
                    description = "ID de la canción que se quiere añadir",
                    schema = @Schema(implementation = Long.class),
                    name = "id2",
                    required = true
            )
    })
    @PostMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<PlaylistDtoResponseAllSongs> addSongToPlaylist(@PathVariable Long id1, @PathVariable Long id2) {
        if(!playlistService.existsById(id1)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            if(!songService.existsById(id2)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else {
                Playlist pl = playlistService.findById(id1).get();
                Song s = songService.findById(id2).get();
                pl.addSong(s);
                playlistService.edit(pl);
                return ResponseEntity.status(HttpStatus.CREATED).body(playlistDtoConverter.toPlayListDtoAllSongs(pl));
            }
        }
    }
    @Operation(summary = "Elimina una canción de la lista de reproducción")
    @ApiResponse(responseCode = "204",
    description = "Se ha borrado la canción de la lista de reproducción",
    content = @Content)
    @Parameters(value = {
            @Parameter(
                    description = "ID de la lista de reproducción de la que eliminar la canción",
                    schema = @Schema(implementation = Long.class),
                    name = "id1",
                    required = true
            ),
            @Parameter(
                    description = "ID de la canción que se quiere eliminar",
                    schema = @Schema(implementation = Long.class),
                    name = "id2",
                    required = true
            )
    })
    @DeleteMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<?> deleteSongFromPlayList(@PathVariable Long id1, @PathVariable Long id2) {
        if(!playlistService.existsById(id1)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            if(!songService.existsById(id2)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else {
                Playlist pl = playlistService.findById(id1).get();
                Song s = songService.findById(id2).get();
                if(pl.getSongs().contains(s)) {
                    while (pl.getSongs().contains(s)) {
                        pl.deleteSong(s);
                        playlistService.edit(pl);
                    }
                }
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

            }
        }
    }



}
