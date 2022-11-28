package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
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

@RestController
@RequiredArgsConstructor
@Tag(name = "Artista", description = "Este es el  controlador de artistas")
public class ArtistController {

    private final ArtistService artistService;

    private final SongService songService;

    @Operation(summary = "Obtiene todos los artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se han encontrado artistas",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 1,
                                                    "name": "Joaquín Sabina"
                                                },
                                                {
                                                    "id": 2,
                                                    "name": "Dua Lipa"
                                                },
                                                {
                                                    "id": 3,
                                                    "name": "Metallica"
                                                }
                                            ]
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se ha encontrado ningún artista",
            content = @Content)
    })
    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> getAllArtists() {
        if(artistService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(artistService.findAll());
        }
    }
    @Operation(summary = "Obtiene un artista en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se ha encontrado el artista",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Artist.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "name": "Joaquín Sabina"
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se ha encontrado el artista por el ID",
            content = @Content)
    })
    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> getArtistById(
            @Parameter(
                    description = "ID del artista a buscar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        if(artistService.existsById(id)) {
            return ResponseEntity.of(artistService.findById(id));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Crea un artista")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
    description = "Cuerpo de la petición",
    content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = Artist.class),
            examples = {
                    @ExampleObject(
                            value = """
                                    {
                                        "name": "Shakira"
                                    }
                                    """
                    )
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
            description = "Se ha creado el artista",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Artist.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 14,
                                                "name": "Shakira"
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "400",
            description = "Los datos del artista son incorrectos",
            content = @Content)
    })
    @PostMapping("/artist/")
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artista) {
        if(artista.getName() != null
                && !artista.getName().isEmpty()
                && !artista.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(artistService.add(artista));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @Operation(summary = "Edita un artista en base a su ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
    description = "Cuerpo de la petición",
    content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = Artist.class),
            examples = {
                    @ExampleObject(
                            value = """
                                    {
                                        "name": "Santa Justa Klan"
                                    }
                                    """
                    )
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se ha editado el artista",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Artist.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 3,
                                                "name": "Santa Justa Klan"
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
                description = "No se ha encontrado el artista por el ID",
                content = @Content)
    })
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editArtist(
            @Parameter(
                    description = "ID del artista a editar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id, @RequestBody Artist artista) {
        if(artistService.existsById(id)) {
            return ResponseEntity.of(artistService.findById(id).map(toEdit -> {
                toEdit.setName(artista.getName());
                return Optional.of(artistService.edit(toEdit));
            }).orElse(Optional.empty()));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Borra un artista en base a su ID")
    @ApiResponse(responseCode = "204",
    description = "Se ha borrado el artista",
    content = @Content)
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<?> deleteArtist(
            @Parameter(
                    description = "ID del artista a borrar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        if(artistService.existsById(id)) {
            songService.findByArtist(artistService.findById(id))
                                                            .stream()
                                                            .forEach(song -> song.setArtist(null));
            artistService.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

