package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    private final SongService songService;


    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> getAllArtists() {
        if(artistService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(artistService.findAll());
        }
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        if(artistService.existsById(id)) {
            return ResponseEntity.of(artistService.findById(id));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

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

    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editArtist(@PathVariable Long id, @RequestBody Artist artista) {
        if(artistService.existsById(id)) {
            return ResponseEntity.of(artistService.findById(id).map(toEdit -> {
                toEdit.setName(artista.getName());
                return Optional.of(artistService.edit(toEdit));
            }).orElse(Optional.empty()));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/artist/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id) {
        if(artistService.existsById(id)) {
            songService.findByArtist(artistService.findById(id))
                                                            .stream()
                                                            .forEach(song -> song.setArtist(null));
            artistService.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

