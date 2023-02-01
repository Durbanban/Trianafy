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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Lista de reproducción", description = "Este es el controlador de listas de reproducción")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SongService songService;

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
    public List<PlaylistDtoResponseAll> getAllPlaylists() {

        List<PlaylistDtoResponseAll> listDto = playlistService.findAll()
                                                                .stream()
                                                                .map(PlaylistDtoConverter::toPlaylistDto)
                                                                .collect(Collectors.toList());
        return listDto;


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
    public PlaylistDtoResponseAllSongs getPlaylistById(
            @Parameter(
                    description = "ID de la lista de reproducción a buscar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {

        return PlaylistDtoConverter.toPlayListDtoAllSongs(playlistService.findById(id));

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

        Playlist playlist = PlaylistDtoConverter.toPlaylist(playlistDtoRequest);

        PlaylistDtoResponseCreation response = PlaylistDtoConverter.toPlayListDtoCreation(playlistService.add(playlist));

        URI createdURI = ServletUriComponentsBuilder
                                    .fromCurrentRequest()
                                    .path("/{id}")
                                    .buildAndExpand(playlist.getId()).toUri();

        return ResponseEntity.created(createdURI).body(response);

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
    public PlaylistDtoResponseAll editPlaylist(
            @Parameter(
                    description = "ID de la lista de reproducción a editar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id, @Valid @RequestBody PlaylistDtoRequest playlistDtoRequest) {

        Playlist playlist = PlaylistDtoConverter.toPlaylist(playlistDtoRequest);

        return PlaylistDtoConverter.toPlaylistDto(playlistService.edit(id, playlist));
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

        playlistService.deleteById(id);
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
    public PlaylistDtoResponseAllSongs getAllSongsByPlaylistId(
            @Parameter(

                    description = "ID de la lista de reproducción a la que consultar todas las canciones",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {


        PlaylistDtoResponseAllSongs playlistDto = PlaylistDtoConverter.toPlayListDtoAllSongs(playlistService.findById(id));
        return playlistDto;

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
    public SongDtoResponseById getSongFromPlayList(@PathVariable Long id1, @PathVariable Long id2) {

        Song song = playlistService.findSongFromPlaylist(id1, id2);

        return SongDtoConverter.toSongDtoById(song);

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
    public PlaylistDtoResponseAllSongs addSongToPlaylist(@PathVariable Long id1, @PathVariable Long id2) {

            Playlist pl = playlistService.findById(id1);
            Song s = songService.findById(id2);
            pl.addSong(s);
            playlistService.add(pl);


            return PlaylistDtoConverter.toPlayListDtoAllSongs(pl);


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
        playlistService.deleteSongFromPlaylist(id1, id2);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
