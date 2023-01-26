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
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Artista", description = "Este es el  controlador de artistas")
@Validated
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
    public List<Artist> getAllArtists() {
        return artistService.findAll();
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
    public Artist getArtistById(
            @Parameter(
                    description = "ID del artista a buscar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        return artistService.findById(id);
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
    public ResponseEntity<Artist> createArtist(@Valid @RequestBody Artist artista) {
        Artist created = artistService.add(artista);

        URI createdURI = ServletUriComponentsBuilder
                                    .fromCurrentRequest()
                                    .path("/{id}")
                                    .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(createdURI).body(created);
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
    public Artist editArtist(
            @Parameter(
                    description = "ID del artista a editar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id, @Valid @RequestBody Artist artista) {
        return artistService.edit(id, artista);
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
            artistService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

