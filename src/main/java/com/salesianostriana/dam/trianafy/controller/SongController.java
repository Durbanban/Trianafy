package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.dto.SongDtoRequest;
import com.salesianostriana.dam.trianafy.dto.SongDtoResponse;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    private final ArtistService artistService;
    private final SongDtoConverter songDtoConverter;

    @GetMapping("/song/")
    public ResponseEntity<List<SongDtoResponse>> getAllSongs() {
        if(songService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            List<SongDtoResponse> result = songService.findAll()
                    .stream()
                    .map(songDtoConverter::toSongDto)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        if(songService.existsById(id)) {
            return ResponseEntity.of(songService.findById(id));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/song/")
    public ResponseEntity<SongDtoResponse> createSong(@RequestBody SongDtoRequest song) {

        if(artistService.existsById(song.getArtistId())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(songService.add("Aquí va la song transformada de songdtorequest"))
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
